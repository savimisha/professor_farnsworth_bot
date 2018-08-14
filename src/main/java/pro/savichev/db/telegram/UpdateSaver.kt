package pro.savichev.db.telegram

import io.reactivex.Completable
import pro.savichev.db.telegram.model.InternalUpdate

interface UpdateSaver {
    fun save(update: InternalUpdate): Completable
}
