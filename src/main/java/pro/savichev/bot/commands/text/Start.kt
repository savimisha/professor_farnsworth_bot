package pro.savichev.bot.commands.text

import com.pengrad.telegrambot.model.Message
import io.reactivex.Completable
import org.springframework.stereotype.Component
import pro.savichev.bot.commands.TextCommand
import pro.savichev.bot.commands.AbstractCommand

@Component
@TextCommand("start")
class Start : AbstractCommand() {

    override fun execute(message: Message): Completable {
        return Completable.fromRunnable { bot.sendMessage(message.chat().id(), "Hello!") }
    }
}
