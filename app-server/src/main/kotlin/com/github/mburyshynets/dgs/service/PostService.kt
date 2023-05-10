package com.github.mburyshynets.dgs.service

import com.github.mburyshynets.dgs.graphql.generated.types.CreatePostRequest
import com.github.mburyshynets.dgs.graphql.generated.types.Post

interface PostService {

    fun createNewPost(request: CreatePostRequest): Post

    fun getPostsByUserIds(keys: Set<Long>): Map<Long, List<Post>>
}
