package pro.savichev.bot.callbacks

import com.pengrad.telegrambot.model.CallbackQuery
import io.reactivex.Completable
import io.reactivex.Single
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import pro.savichev.db.telegram.repository.UserRepository

@Component
abstract class AdminCallback: AbstractCallback() {

    @Autowired
    private lateinit var userRepository: UserRepository

    override fun execute(callback: CallbackQuery): Completable {
        return Single.fromCallable<Completable> {
            if (callback.message() == null || callback.data() == null) {
                bot.answerCallbackQuery(callback.id(), null)
                return@fromCallable Completable.complete()
            }
            val from = callback.from()
            val optional = userRepository.findById(from.id())
            if (optional.isPresent) {
                if (optional.get().isAdmin) {
                    return@fromCallable executeAdmin(callback)
                } else {
                    bot.answerCallbackQuery(callback.id(), null)
                    throw Exception("Trying to execute admin command from non-admin user " + optional.get().id + ".")
                }
            } else {
                bot.answerCallbackQuery(callback.id(), null)
                throw Exception("User not found.")
            }
        }.flatMapCompletable { completable -> completable }
    }

    abstract fun executeAdmin(callback: CallbackQuery): Completable
}