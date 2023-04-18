package com.github.mburyshynets.dgs.data.repository

import com.github.mburyshynets.dgs.data.model.PostExtension
import org.springframework.data.repository.CrudRepository

interface PostExtensionRepository : CrudRepository<PostExtension, Long> {

    fun findAllByPostIdIn(postIds: Collection<Long>): List<PostExtension>
}
