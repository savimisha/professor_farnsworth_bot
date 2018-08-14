package pro.savichev.bot.commands.text

import com.pengrad.telegrambot.model.Message
import com.pengrad.telegrambot.model.request.ParseMode
import io.reactivex.Completable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import pro.savichev.bot.commands.AdminCommand
import pro.savichev.bot.commands.TextCommand
import pro.savichev.db.Delay
import pro.savichev.db.DelayRepository
import pro.savichev.db.VideoRepository
import pro.savichev.db.log.Log
import pro.savichev.db.telegram.model.InternalChat
import pro.savichev.db.telegram.repository.ChatRepository
import java.util.concurrent.TimeUnit

@Component
@TextCommand("start_sending")
class StartSending : AdminCommand() {

    companion object {
        private var TAG = StartSending::class.simpleName!!
    }

    @Autowired
    private lateinit var videoRepository: VideoRepository

    @Autowired
    private lateinit var chatRepository: ChatRepository

    @Autowired
    private lateinit var delayRepository: DelayRepository

    @Autowired
    private lateinit var log: Log

    private var currentId = 1

    var disposable: Disposable? = null

    override fun executeAdmin(message: Message): Completable {
        val text = message.text()
        val split = text.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val chatID = message.chat().id()!!
        if (split.size < 2) {
            bot.sendMessage(chatID, "<b>Ошибка.</b> Ну параметр то задай.", ParseMode.HTML)
            return Completable.complete()
        }
        val firstLength = split[0].length
        val argument = Integer.parseInt(text.substring(firstLength + 1))
        currentId = argument
        val delay = delayRepository.findById(1).orElse(Delay(delay = 1200000)).delay
        val chats = chatRepository.findAllBySubscribed(true)
        disposable = Completable.create { emitter1 ->
            val videoOptional = videoRepository.findById(currentId)
            if (!videoOptional.isPresent) {
                emitter1.onComplete()
                return@create
            }
            val videoLink = videoOptional.get().link
            for (chat: InternalChat in chats) {
                bot.sendMessage(chat.id, "Good news everyone!\n$videoLink")
            }
            currentId++
            if (currentId <= videoRepository.count()) {
                emitter1.onComplete()
            } else {
                emitter1.onError(Exception("All video sent."))
            }
        }.repeatWhen{ objectObservable -> objectObservable.delay(delay, TimeUnit.MILLISECONDS) }
                .subscribeOn(Schedulers.io())
                .subscribeBy(onError = { throwable -> log.e(TAG, "", throwable) })
        return Completable.complete()
    }
}