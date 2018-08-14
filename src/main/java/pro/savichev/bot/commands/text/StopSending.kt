package pro.savichev.bot.commands.text

import com.pengrad.telegrambot.model.Message
import io.reactivex.Completable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import pro.savichev.bot.commands.AdminCommand
import pro.savichev.bot.commands.TextCommand

@Component
@TextCommand("stop_sending")
class StopSending: AdminCommand() {

    @Autowired
    private lateinit var startSending: StartSending

    override fun executeAdmin(message: Message): Completable {
        return Completable.fromRunnable {
            startSending.disposable?.dispose()
            bot.sendMessage(message.chat().id(), "Ok")
        }
    }
}