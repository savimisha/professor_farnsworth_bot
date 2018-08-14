package pro.savichev.db.telegram.model

import com.pengrad.telegrambot.model.Message
import com.pengrad.telegrambot.model.Update

import javax.persistence.*

@Entity
@Table(name = "updates")
data class InternalUpdate(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int = 0,
        @ManyToOne
        @JoinColumn(name = "from_id", referencedColumnName = "id")
        var user: InternalUser? = null,
        var date: Int? = null,
        @ManyToOne
        @JoinColumn(name = "chat_id", referencedColumnName = "id")
        var chat: InternalChat? = null,
        @Column(name = "author_signature")
        var authorSignature: String? = null,
        var text: String? = null,
        var location: String? = null,
        @ManyToOne
        @JoinColumn(name = "left_chat_member", referencedColumnName = "id")
        var leftChatMember: InternalUser? = null,
        @Column(name = "group_chat_created")
        var groupChatCreated: Boolean? = null,
        @Column(name = "migrate_to_chat_id")
        var migrateToChatId: Long? = null,
        @Column(name = "migrate_from_chat_id")
        var migrateFromChatId: Long? = null,

        @OneToOne
        @JoinColumn(name = "callback_id", referencedColumnName = "id")
        var callback: InternalCallback? = null) {

    constructor(update: Update) : this() {
        var message: Message? = update.message()
        if (message == null) {
            message = update.editedMessage()
        }
        if (message == null) {
            message = update.channelPost()
        }
        if (message == null) {
            message = update.editedChannelPost()
        }
        if (message != null) {
            val from = message.from()
            if (from != null) {
                user = InternalUser(from)
            }
            date = message.date()
            chat = InternalChat(message.chat())
            authorSignature = message.authorSignature()
            text = message.text()
            val loc = message.location()
            if (loc != null) {
                location = loc.toString()
            }
            val leftChatMember = message.leftChatMember()
            if (leftChatMember != null) {
                this.leftChatMember = InternalUser(leftChatMember)
            }
            groupChatCreated = message.groupChatCreated()
            migrateToChatId = message.migrateToChatId()
            migrateFromChatId = message.migrateFromChatId()
        }
        if (update.callbackQuery() != null) {
            callback = InternalCallback(update.callbackQuery())
        }
    }

}
