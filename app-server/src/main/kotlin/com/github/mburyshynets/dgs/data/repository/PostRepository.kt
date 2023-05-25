package com.github.mburyshynets.dgs.data.repository

import com.github.mburyshynets.dgs.data.model.PostEntity
import org.springframework.data.repository.CrudRepository

interface PostRepository : CrudRepository<PostEntity, Long> {

    fun findAllByUserIdIn(userIds: Collection<Long>): List<PostEntity>

    fun findAllByTopicIdIn(topicIds: Collection<Long>): List<PostEntity>

    fun findAllByUserId(userId: Long): List<PostEntity>

    fun findAllByTopicId(topicId: Long): List<PostEntity>
}
