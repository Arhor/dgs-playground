package com.github.mburyshynets.dgs.service

import com.github.mburyshynets.dgs.graphql.generated.types.CreatePostExtensionRequest
import com.github.mburyshynets.dgs.graphql.generated.types.PostExtensionDto

interface PostExtensionService {

    fun createNewPostExtension(request: CreatePostExtensionRequest): PostExtensionDto

    fun getPostsByPostIds(keys: Set<Long>): Map<Long, List<PostExtensionDto>>
}
