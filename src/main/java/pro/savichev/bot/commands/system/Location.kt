package pro.savichev.bot.commands.system

import com.pengrad.telegrambot.model.Message
import io.reactivex.Completable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import pro.savichev.bot.commands.AbstractCommand
import pro.savichev.bot.commands.BeanNames
import pro.savichev.db.telegram.repository.ChatRepository


@Component(BeanNames.LOCATION)
class Location : AbstractCommand() {

    private var chatRepository: ChatRepository? = null

    override fun execute(message: Message): Completable {
        return Completable.complete()
    }

    @Autowired
    fun setChatRepository(chatRepository: ChatRepository) {
        this.chatRepository = chatRepository
    }
}
