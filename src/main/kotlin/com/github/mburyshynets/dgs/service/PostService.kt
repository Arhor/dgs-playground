package com.github.mburyshynets.dgs.service

import com.github.mburyshynets.dgs.graphql.generated.types.PostDto

interface PostService {

    fun createNewPost(userId: Long, content: String): PostDto

    fun getPostsByUserIds(keys: Set<Long>): Map<Long, List<PostDto>>
}
