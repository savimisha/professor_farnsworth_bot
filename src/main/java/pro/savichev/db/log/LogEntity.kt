package pro.savichev.db.log

import javax.persistence.*

@Entity
@Table(name = "logs")
data class LogEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,
    val tag: String,
    val type: String,
    val message: String,
    val timestamp: Long
)


