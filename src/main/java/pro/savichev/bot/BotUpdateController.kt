package pro.savichev.bot;



import com.pengrad.telegrambot.model.Update
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import pro.savichev.Config
import pro.savichev.db.log.Log
import pro.savichev.db.telegram.UpdateSaver
import pro.savichev.db.telegram.model.InternalUpdate
import javax.annotation.PostConstruct;

@Controller
open class BotUpdateController {

    companion object {
        val TAG = BotUpdateController::class.simpleName!!
    }

    @Autowired
    private lateinit var botUpdateResolver: BotUpdateResolver
    @Autowired
    private lateinit var updateSaver: UpdateSaver
    @Autowired
    private lateinit var log: Log
    @Autowired
    private lateinit var bot: Bot

    @PostConstruct
    open fun init() {
        bot.setWebhook(Config.SERVER_URL + Config.BOT_WEBHOOK_PATH)
    }

    @PostMapping(Config.BOT_WEBHOOK_PATH)
    @ResponseStatus(value = HttpStatus.OK)
    open fun onUpdate(@RequestBody update: Update) {
        updateSaver.save(InternalUpdate(update))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .andThen(botUpdateResolver.resolve(update))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribeBy(onError = { log.e(TAG, "", it)})
    }
}
