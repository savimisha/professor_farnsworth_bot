package pro.savichev.bot.callbacks

import com.pengrad.telegrambot.model.CallbackQuery
import io.reactivex.Completable

interface Callback {
    fun execute(callback: CallbackQuery): Completable
}