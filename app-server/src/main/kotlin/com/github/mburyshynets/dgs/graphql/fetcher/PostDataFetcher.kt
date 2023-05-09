package com.github.mburyshynets.dgs.graphql.fetcher

import com.github.mburyshynets.dgs.graphql.generated.DgsConstants
import com.github.mburyshynets.dgs.graphql.generated.types.CreatePostRequest
import com.github.mburyshynets.dgs.graphql.generated.types.PostDto
import com.github.mburyshynets.dgs.graphql.generated.types.UserDto
import com.github.mburyshynets.dgs.graphql.loader.PostDataLoader
import com.github.mburyshynets.dgs.service.PostService
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.InputArgument
import java.util.concurrent.CompletableFuture

@DgsComponent
class PostDataFetcher(private val postService: PostService) {

    @DgsData(parentType = DgsConstants.USERDTO.TYPE_NAME)
    fun posts(dfe: DgsDataFetchingEnvironment): CompletableFuture<List<PostDto>> {
        val dataLoader = dfe.getDataLoader<Long, List<PostDto>>(PostDataLoader::class.java)
        val source = dfe.getSource<UserDto>()

        return dataLoader.load(source.id)
    }

    @DgsMutation
    fun createPost(@InputArgument request: CreatePostRequest): PostDto {
        return postService.createNewPost(request)
    }
}
