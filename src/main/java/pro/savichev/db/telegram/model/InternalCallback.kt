package pro.savichev.db.telegram.model

import com.pengrad.telegrambot.model.CallbackQuery

import javax.persistence.*

@Entity
@Table(name = "callback")
data class InternalCallback(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0,
    @Column(name = "callback_id")
    var callbackId: String? = null,
    @ManyToOne
    @JoinColumn(name = "from_id", referencedColumnName = "id")
    var user: InternalUser? = null,
    @Column(name = "message_id")
    var messageId: Int? = null,
    @ManyToOne
    @JoinColumn(name = "chat_id", referencedColumnName = "id")
    var chat: InternalChat? = null,
    var data: String? = null) {

    internal constructor(callbackQuery: CallbackQuery): this() {
        user = InternalUser(callbackQuery.from())
        callbackId = callbackQuery.id()
        callbackQuery.message()?.let {
            messageId = it.messageId()
            chat = InternalChat(it.chat())
        }
        data = callbackQuery.data()
    }

}
