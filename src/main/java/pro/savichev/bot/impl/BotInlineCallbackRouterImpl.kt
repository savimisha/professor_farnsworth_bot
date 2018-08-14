package pro.savichev.bot.impl

import io.reactivex.Maybe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.core.annotation.AnnotationUtils
import org.springframework.stereotype.Component
import pro.savichev.bot.BotInlineCallbackRouter
import pro.savichev.bot.callbacks.AbstractCallback
import pro.savichev.bot.callbacks.TextCallback
import java.util.HashMap
import javax.annotation.PostConstruct

@Component
class BotInlineCallbackRouterImpl : BotInlineCallbackRouter {

    private val mapping = HashMap<String, Class<out AbstractCallback>>()

    @Autowired
    private lateinit var applicationContext: ApplicationContext

    @PostConstruct
    fun init() {
        val beans = applicationContext.getBeansWithAnnotation(TextCallback::class.java)
        beans.forEach { _, value ->
            val textCommand = AnnotationUtils.findAnnotation(value.javaClass, TextCallback::class.java)
            if (textCommand != null) {
                val mappingValue = textCommand.value
                mapping[mappingValue] = (value as AbstractCallback).javaClass
            }
        }
    }

    override fun getRoute(text: String): Maybe<AbstractCallback> {
       return Maybe.create { emitter ->
           val clazz = mapping[getCommand(text)]
           if (clazz != null) {
               emitter.onSuccess(applicationContext.getBean(clazz))
           }
       }
    }


    private fun getCommand(text: String): String {
        return text.split("-")[0]
    }
}