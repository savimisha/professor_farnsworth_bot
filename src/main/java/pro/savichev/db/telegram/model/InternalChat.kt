package pro.savichev.db.telegram.model


import com.pengrad.telegrambot.model.Chat

import javax.persistence.*

@Entity
@Table(name = "chat")
data class InternalChat(
        @Id
        var id: Long = 0L,
        var type: String = "",
        var title: String? = null,
        var username: String? = null,
        @Column(name = "first_name")
        var firstName: String? = null,
        @Column(name = "last_name")
        var lastName: String? = null,
        var active: Boolean = true,
        var subscribed: Boolean = false) {

    internal constructor(chat: Chat) : this() {
        id = chat.id()
        type = chat.type().toString()
        title = chat.title()
        username = chat.username()
        firstName = chat.firstName()
        lastName = chat.lastName()
    }
}
