package com.github.mburyshynets.dgs.data.repository

import com.github.mburyshynets.dgs.data.model.TopicEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository

interface TopicRepository : CrudRepository<TopicEntity, Long>, PagingAndSortingRepository<TopicEntity, Long> {
}
