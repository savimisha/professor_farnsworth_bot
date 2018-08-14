package pro.savichev.db.telegram.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import pro.savichev.db.telegram.model.InternalCallback

@Repository
interface CallbackRepository : CrudRepository<InternalCallback, Int>
