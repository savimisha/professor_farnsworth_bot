package pro.savichev.bot.impl

import com.pengrad.telegrambot.model.CallbackQuery
import com.pengrad.telegrambot.model.Message
import com.pengrad.telegrambot.model.Update
import io.reactivex.Completable
import pro.savichev.bot.BotInlineCallbackRouter
import pro.savichev.bot.BotTextCommandRouter
import pro.savichev.bot.BotUpdateResolver
import pro.savichev.bot.commands.Command
import pro.savichev.bot.commands.BeanNames
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import pro.savichev.db.log.Log

@Component
class BotUpdateResolverImpl : BotUpdateResolver {

    @Autowired
    private lateinit var botTextCommandRouter: BotTextCommandRouter
    @Autowired
    private lateinit var botInlineCallbackRouter: BotInlineCallbackRouter
    @Autowired
    private lateinit var applicationContext: ApplicationContext

    override fun resolve(update: Update): Completable {
        var message: Message? = update.message()
        if (message == null) {
            message = update.editedMessage()
        }
        if (message == null) {
            message = update.channelPost()
        }
        if (message == null) {
            message = update.editedChannelPost()
        }
        if (message != null) {
            return resolveMessage(message)
        }
        return if (update.callbackQuery() != null) {
            resolveCallback(update.callbackQuery())
        } else Completable.complete()
    }

    private fun resolveMessage(message: Message): Completable {
        if (message.text() != null) {
            return botTextCommandRouter.getRoute(message.text())
                    .flatMapCompletable { command -> command.execute(message) }
        }
        if (message.location() != null) {
            return (applicationContext.getBean(BeanNames.LOCATION) as Command).execute(message)
        }
        if (message.leftChatMember() != null) {
            return (applicationContext.getBean(BeanNames.LEFT_CHAT_MEMBER) as Command).execute(message)
        }
        return if (message.migrateFromChatId() != null) {
            (applicationContext.getBean(BeanNames.MIGRATE_FROM_CHAT_ID) as Command).execute(message)
        } else Completable.complete()
    }

    private fun resolveCallback(callbackQuery: CallbackQuery): Completable {
        return if (callbackQuery.data() != null) {
            botInlineCallbackRouter.getRoute(callbackQuery.data())
                    .flatMapCompletable { callback -> callback.execute(callbackQuery) }
        } else Completable.complete()
    }

}
