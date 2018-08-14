package pro.savichev.bot.commands

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import pro.savichev.bot.Bot

@Component
abstract class AbstractCommand : Command {
    @Autowired
    protected lateinit var bot: Bot
}
