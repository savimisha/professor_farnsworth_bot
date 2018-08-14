package pro.savichev.db.log

import io.reactivex.Completable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.io.PrintWriter
import java.io.StringWriter


@Component
class LogImpl : Log {

    @Autowired
    private lateinit var logRepository: LogRepository
    @Autowired
    private lateinit var consoleLogger: Logger

    override fun i(tag: String, message: String) {
        log(tag, LogType.INFO, message)
    }

    override fun i(tag: String, message: String, throwable: Throwable) {
        log(tag, LogType.INFO, message, throwable)
    }

    override fun e(tag: String, message: String) {
        log(tag, LogType.ERROR, message)
    }

    override fun e(tag: String, message: String, throwable: Throwable) {
        log(tag, LogType.ERROR, message, throwable)
    }

    private fun log(tag: String, type: String, message: String, throwable: Throwable)
    {
        log(tag, type, message + "\n" + getStackTrace(throwable))
    }


    private fun log(tag: String, type: String, message: String) {
        val entity = LogEntity(tag = tag, type = type, message = message, timestamp = System.currentTimeMillis())
        //noinspection ResultOfMethodCallIgnored
        Completable.create { logRepository.save(entity) }
                .subscribeOn(Schedulers.io())
                .subscribeBy(onError = { consoleLogger.error("", it) })
    }

    private fun getStackTrace(throwable: Throwable): String
    {
        val sw = StringWriter()
        val pw = PrintWriter(sw, true)
        throwable.printStackTrace(pw)
        return sw.buffer.toString()
    }

}
