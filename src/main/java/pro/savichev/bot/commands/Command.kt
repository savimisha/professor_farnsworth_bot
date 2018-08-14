package pro.savichev.bot.commands

import com.pengrad.telegrambot.model.Message
import io.reactivex.Completable

interface Command {
    fun execute(message: Message): Completable
}
