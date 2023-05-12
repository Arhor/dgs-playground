package com.github.mburyshynets.dgs.web.graphql.fetcher

import com.github.mburyshynets.dgs.graphql.generated.DgsConstants
import com.github.mburyshynets.dgs.graphql.generated.types.CreatePostRequest
import com.github.mburyshynets.dgs.graphql.generated.types.Post
import com.github.mburyshynets.dgs.graphql.generated.types.User
import com.github.mburyshynets.dgs.service.PostService
import com.github.mburyshynets.dgs.web.graphql.loader.PostsBatchLoader
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.InputArgument
import java.util.concurrent.CompletableFuture

@DgsComponent
class PostFetcher(private val postService: PostService) {

    @DgsData(parentType = DgsConstants.USER.TYPE_NAME)
    fun posts(dfe: DgsDataFetchingEnvironment): CompletableFuture<List<Post>> {
        val dataLoader = dfe.getDataLoader<Long, List<Post>>(PostsBatchLoader::class.java)
        val source = dfe.getSource<User>()

        return dataLoader.load(source.id)
    }

    @DgsMutation
    fun createPost(@InputArgument request: CreatePostRequest): Post {
        return postService.createNewPost(request)
    }
}
