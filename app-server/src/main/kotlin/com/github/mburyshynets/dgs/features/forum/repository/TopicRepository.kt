package com.github.mburyshynets.dgs.features.forum.repository

import com.github.mburyshynets.dgs.features.forum.entity.TopicEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository

interface TopicRepository : CrudRepository<TopicEntity, Long>, PagingAndSortingRepository<TopicEntity, Long> {
}
