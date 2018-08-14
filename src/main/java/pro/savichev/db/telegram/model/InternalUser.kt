package pro.savichev.db.telegram.model


import com.pengrad.telegrambot.model.User

import javax.persistence.*

@Entity
@Table(name = "user")
data class InternalUser(
        @Id
        @Column
        var id: Int = 0,
        @Column(name = "is_bot")
        var isBot: Boolean = false,
        @Column(name = "first_name")
        var firstName: String = "",
        @Column(name = "last_name")
        var lastName: String? = null,
        var username: String? = null,
        @Column(name = "is_admin")
        var isAdmin: Boolean = false) {

    internal constructor(user: User) : this() {
        id = user.id()
        isBot = user.isBot
        firstName = user.firstName()
        lastName = user.lastName()
        username = user.username()
    }

}
