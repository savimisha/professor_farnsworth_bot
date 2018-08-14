package pro.savichev.bot

import io.reactivex.Maybe
import pro.savichev.bot.commands.Command

interface BotTextCommandRouter {
    fun getRoute(text: String): Maybe<Command>
}
