package pro.savichev.db

import javax.persistence.*

@Entity
@Table(name = "delay")
data class Delay(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int = 0,
        val delay: Long = 0L
)