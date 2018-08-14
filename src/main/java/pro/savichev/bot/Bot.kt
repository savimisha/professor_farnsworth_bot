package pro.savichev.bot

import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup
import com.pengrad.telegrambot.model.request.Keyboard
import com.pengrad.telegrambot.model.request.ParseMode

import java.io.File

interface Bot {
    fun sendMessage(chatID: Long, text: String, parseMode: ParseMode? = null, keyboard: Keyboard? = null)
    fun sendMessageSync(chatID: Long, text: String)
    fun answerCallbackQuery(callbackQueryID: String, text: String? = null)
    fun editMessageText(chatID: Long, messageID: Int, newText: String, inlineKeyboardMarkup: InlineKeyboardMarkup? = null)
    fun editMessageReplyMarkup(chatID: Long, messageID: Int, inlineKeyboardMarkup: InlineKeyboardMarkup)
    fun sendPhoto(chatID: Long, filePath: String, caption: String = "")
    fun sendPhotoSync(chatID: Long, filePath: String, caption: String = "")
    fun setWebhook(url: String)
}
