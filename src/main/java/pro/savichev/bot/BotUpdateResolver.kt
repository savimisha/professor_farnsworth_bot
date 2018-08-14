package pro.savichev.bot

import com.pengrad.telegrambot.model.Update
import io.reactivex.Completable

interface BotUpdateResolver {
    fun resolve(update: Update): Completable
}
