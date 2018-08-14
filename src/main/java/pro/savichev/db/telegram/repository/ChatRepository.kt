package pro.savichev.db.telegram.repository

import org.springframework.data.repository.CrudRepository
import pro.savichev.db.telegram.model.InternalChat
import org.springframework.stereotype.Repository

@Repository
interface ChatRepository : CrudRepository<InternalChat, Long> {
    fun findAllBySubscribed(subscribed: Boolean): Iterable<InternalChat>
}
