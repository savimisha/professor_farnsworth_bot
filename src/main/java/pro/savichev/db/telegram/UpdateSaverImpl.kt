package pro.savichev.db.telegram

import io.reactivex.*
import pro.savichev.db.telegram.model.*
import pro.savichev.db.telegram.repository.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


@Component
class UpdateSaverImpl : UpdateSaver {

    @Autowired
    private lateinit var userRepository: UserRepository
    @Autowired
    private lateinit var chatRepository: ChatRepository
    @Autowired
    private lateinit var updateRepository: UpdateRepository
    @Autowired
    private lateinit var callbackRepository: CallbackRepository


    override fun save(update: InternalUpdate): Completable {
        return Completable.fromAction {
            update.chat?.let { saveChat(it) }
            update.user?.let { saveUser(it) }
            update.leftChatMember?.let { saveUser(it) }
            update.callback?.let { callbackRepository.save(it) }
            updateRepository.save(update)
        }
    }

    private fun saveUser(user: InternalUser) {
        val optional = userRepository.findById(user.id)
        optional.ifPresent { internalUser -> user.isAdmin = internalUser.isAdmin }
        userRepository.save(user)
    }

    private fun saveChat(chat: InternalChat) {
        val optional = chatRepository.findById(chat.id)
        optional.ifPresent { internalChat ->
            chat.active = internalChat.active
            chat.subscribed = internalChat.subscribed
        }
        chatRepository.save(chat)
    }
}
