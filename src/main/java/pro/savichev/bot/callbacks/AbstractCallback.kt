package pro.savichev.bot.callbacks

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import pro.savichev.bot.Bot


@Component
abstract class AbstractCallback: Callback {

    @Autowired
    protected lateinit var bot: Bot
}