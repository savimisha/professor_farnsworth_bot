package pro.savichev.bot.commands.system

import com.pengrad.telegrambot.model.Message
import io.reactivex.Completable
import pro.savichev.bot.commands.BeanNames
import pro.savichev.bot.commands.AbstractCommand
import org.springframework.stereotype.Component

@Component(BeanNames.LEFT_CHAT_MEMBER)
class LeftChatMember : AbstractCommand() {
    override fun execute(message: Message): Completable {
        return Completable.complete()
    }
}
