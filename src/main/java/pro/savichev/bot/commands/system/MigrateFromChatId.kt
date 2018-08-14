package pro.savichev.bot.commands.system

import com.pengrad.telegrambot.model.Message
import io.reactivex.Completable
import pro.savichev.bot.commands.BeanNames
import pro.savichev.bot.commands.AbstractCommand
import pro.savichev.db.telegram.model.InternalChat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import pro.savichev.db.telegram.repository.ChatRepository

import java.util.Optional

@Component(BeanNames.MIGRATE_FROM_CHAT_ID)
class MigrateFromChatId : AbstractCommand() {

    @Autowired
    private lateinit var chatRepository: ChatRepository

    override fun execute(message: Message): Completable {
        return Completable.fromRunnable {
            val newChatID = message.chat().id()
            val oldChatID = message.migrateFromChatId()
            val newChatOptional = chatRepository.findById(newChatID!!)
            val oldChatOptional = chatRepository.findById(oldChatID!!)

            if (newChatOptional.isPresent && oldChatOptional.isPresent) {
                val newChat = newChatOptional.get()
                val oldChat = oldChatOptional.get()
                newChat.active = oldChat.active
                chatRepository.save(newChat)
            }
        }
    }


}
