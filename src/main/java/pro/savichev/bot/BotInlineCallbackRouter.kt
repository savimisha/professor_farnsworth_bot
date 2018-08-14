package pro.savichev.bot

import io.reactivex.Maybe
import pro.savichev.bot.callbacks.AbstractCallback

interface BotInlineCallbackRouter {
    fun getRoute(text: String): Maybe<AbstractCallback>
}