package pro.savichev.db

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface VideoRepository: CrudRepository<Video, Int>