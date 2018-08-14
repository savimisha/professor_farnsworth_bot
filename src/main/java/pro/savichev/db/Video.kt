package pro.savichev.db

import javax.persistence.*

@Entity
@Table(name = "video")
data class Video(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int = 0,
        val link: String = ""
)