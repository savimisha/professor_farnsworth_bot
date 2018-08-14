package pro.savichev.bot.commands

import com.pengrad.telegrambot.model.Message
import io.reactivex.Completable
import io.reactivex.Single
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import pro.savichev.db.telegram.repository.UserRepository

@Component
abstract class AdminCommand : AbstractCommand() {

    @Autowired
    private lateinit var userRepository: UserRepository

    override fun execute(message: Message): Completable {
        return Single.fromCallable<Completable> {
            message.from() ?: return@fromCallable Completable.error(Exception("Admin command from unknown user."))
            val optional = userRepository.findById(message.from().id()!!)
            if (optional.isPresent) {
                if (optional.get().isAdmin) {
                    return@fromCallable executeAdmin(message)
                } else {
                    return@fromCallable Completable.error(Exception("Trying to execute admin command from non-admin user " + optional.get().id + "."))
                }
            } else {
                return@fromCallable Completable.error(Exception("User not found."))
            }
        }.flatMapCompletable { completable -> completable }
    }

    @Throws(Exception::class)
    abstract fun executeAdmin(message: Message): Completable

}
