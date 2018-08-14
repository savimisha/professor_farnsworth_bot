package pro.savichev.bot.aop

import com.pengrad.telegrambot.model.Update
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


@Aspect
@Component
class BotUpdateControllerAspect {

    @Autowired
    private lateinit var consoleLogger: Logger

    @Pointcut("execution(* pro.savichev.bot.BotUpdateController.onUpdate(..)) && args(update,..)")
    private fun executionOnUpdate(update: Update) {}

    @Around(value = "executionOnUpdate(update)", argNames = "joinPoint, update")
    @Throws(Throwable::class)
    private fun aroundOnUpdate(joinPoint: ProceedingJoinPoint, update: Update): Any? {
        consoleLogger.info("NEW UPDATE!!!")
        return joinPoint.proceed()
    }

}
