package pro.savichev.db.log

interface Log {
    fun i(tag: String, message: String)
    fun i(tag: String, message: String, throwable: Throwable)
    fun e(tag: String, message: String)
    fun e(tag: String, message: String, throwable: Throwable)
}
