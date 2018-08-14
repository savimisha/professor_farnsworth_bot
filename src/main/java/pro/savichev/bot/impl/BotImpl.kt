package pro.savichev.bot.impl

import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup
import com.pengrad.telegrambot.model.request.Keyboard
import com.pengrad.telegrambot.model.request.ParseMode
import com.pengrad.telegrambot.request.*
import com.pengrad.telegrambot.response.BaseResponse
import com.pengrad.telegrambot.response.SendResponse
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import pro.savichev.bot.Bot
import pro.savichev.db.log.Log

import java.io.File

@Component
class BotImpl @Autowired
constructor(private val telegramBot: TelegramBot) : Bot {

    companion object {
        private val TAG = BotImpl::class.java.simpleName

        private val MAX_RETRIES = 5
    }

    @Autowired
    private lateinit var log: Log

    override fun sendMessage(chatID: Long, text: String, parseMode: ParseMode?, keyboard: Keyboard?) {
        val sendMessage = SendMessage(chatID, text)
        parseMode?.let { sendMessage.parseMode(it) }
        keyboard?.let { sendMessage.replyMarkup(it) }
        send(sendMessage)
    }

    override fun sendMessageSync(chatID: Long, text: String) {
        val sendMessage = SendMessage(chatID, text)
        sendMessage.disableWebPagePreview(true)
        sendMessage.parseMode(ParseMode.HTML)
        sendSync(sendMessage)
    }

    override fun editMessageText(chatID: Long, messageID: Int, newText: String, inlineKeyboardMarkup: InlineKeyboardMarkup?) {
        val editMessageText = EditMessageText(chatID, messageID, newText)
        editMessageText.parseMode(ParseMode.HTML)
        inlineKeyboardMarkup?.let { editMessageText.replyMarkup(it) }
        send(editMessageText)
    }

    override fun editMessageReplyMarkup(chatID: Long, messageID: Int, inlineKeyboardMarkup: InlineKeyboardMarkup) {
        val editMessageReplyMarkup = EditMessageReplyMarkup(chatID, messageID)
        editMessageReplyMarkup.replyMarkup(inlineKeyboardMarkup)
        send(editMessageReplyMarkup)
    }

    override fun sendPhoto(chatID: Long, filePath: String, caption: String) {
        val sendPhoto = SendPhoto(chatID, File(filePath))
        sendPhoto.caption(caption)
        send(sendPhoto)
    }

    override fun sendPhotoSync(chatID: Long, filePath: String, caption: String) {
        val sendPhoto = SendPhoto(chatID, File(filePath))
        sendPhoto.caption(caption)
        sendSync(sendPhoto)
    }

    override fun answerCallbackQuery(callbackQueryID: String, text: String?) {
        val answerCallbackQuery = AnswerCallbackQuery(callbackQueryID)
        answerCallbackQuery.text(text)
        send(answerCallbackQuery)
    }

    override fun setWebhook(url: String) {
        val setWebhook = SetWebhook()
        setWebhook.url(url)
        send(setWebhook)
    }

    private fun <T : BaseRequest<T, R>, R : BaseResponse> send(request: BaseRequest<T, R>) {

        telegramBot.send(request)
                .retry(MAX_RETRIES.toLong())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribeWith<DisposableSingleObserver<R>>(object : DisposableSingleObserver<R>() {
                    override fun onSuccess(r: R) {
                        if (!r.isOk) {
                            log.e(TAG, "Telegram API error: " + r.errorCode() + " - " + r.description())
                        }
                    }

                    override fun onError(e: Throwable) {
                        log.e(TAG, "", e)
                    }
                })
    }

    private fun <T : BaseRequest<T, R>, R : BaseResponse> sendSync(request: BaseRequest<T, R>): R {
        return telegramBot.send(request)
                .retry(MAX_RETRIES.toLong())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .blockingGet()
    }



}
