package pro.savichev.bot.impl

import io.reactivex.Maybe
import pro.savichev.Config
import pro.savichev.bot.commands.TextCommand
import pro.savichev.bot.BotTextCommandRouter
import pro.savichev.bot.commands.Command
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.core.annotation.AnnotationUtils
import org.springframework.stereotype.Component


import javax.annotation.PostConstruct
import java.util.HashMap

@Component
class BotTextCommandRouterImpl : BotTextCommandRouter {

    companion object {
        private val mapping = HashMap<String, Class<out Command>>()
    }

    @Autowired
    private lateinit var applicationContext: ApplicationContext

    @PostConstruct
    fun init() {
        val beans = applicationContext.getBeansWithAnnotation(TextCommand::class.java)
        beans.forEach { _, value ->
            val textCommand = AnnotationUtils.findAnnotation(value.javaClass, TextCommand::class.java)!!
            val mappingValue = textCommand.value
            mapping[mappingValue] = value.javaClass.asSubclass(Command::class.java)
        }
    }

    override fun getRoute(text: String): Maybe<Command> {
        return Maybe.create { emitter ->
            val clazz = mapping[getCommand(text)]
            if (clazz != null) {
                emitter.onSuccess(applicationContext.getBean(clazz))
                return@create
            }
            emitter.onComplete()
        }
    }

    private fun getCommand(text: String): String {
        var first = text.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
        first = first.replace("/".toRegex(), "")
        if (first.endsWith(Config.BOT_USERNAME)) {
            first = first.substring(0, first.length - Config.BOT_USERNAME.length - 1)
        }
        return first.toLowerCase()
    }


}
