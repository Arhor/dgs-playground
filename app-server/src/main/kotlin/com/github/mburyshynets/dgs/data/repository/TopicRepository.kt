package com.github.mburyshynets.dgs.data.repository

import com.github.mburyshynets.dgs.data.model.TopicEntity
import org.springframework.data.repository.CrudRepository

interface TopicRepository : CrudRepository<TopicEntity, Long> {
}
