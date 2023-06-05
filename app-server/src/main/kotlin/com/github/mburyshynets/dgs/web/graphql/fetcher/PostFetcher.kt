package com.github.mburyshynets.dgs.web.graphql.fetcher

import com.github.mburyshynets.dgs.graphql.generated.DgsConstants.TOPIC
import com.github.mburyshynets.dgs.graphql.generated.DgsConstants.USER
import com.github.mburyshynets.dgs.graphql.generated.types.CreatePostRequest
import com.github.mburyshynets.dgs.graphql.generated.types.Post
import com.github.mburyshynets.dgs.graphql.generated.types.Topic
import com.github.mburyshynets.dgs.graphql.generated.types.User
import com.github.mburyshynets.dgs.service.ExtendedUserDetails
import com.github.mburyshynets.dgs.service.PostService
import com.github.mburyshynets.dgs.web.graphql.loader.TopicPostsBatchLoader
import com.github.mburyshynets.dgs.web.graphql.loader.UserPostsBatchLoader
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.InputArgument
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import java.util.concurrent.CompletableFuture

@DgsComponent
class PostFetcher(private val postService: PostService) {

    @DgsMutation
    @PreAuthorize("isAuthenticated()")
    fun createPost(
        @InputArgument request: CreatePostRequest,
        @AuthenticationPrincipal currentUser: ExtendedUserDetails,
    ): Post {
        return postService.createNewPost(request, currentUser)
    }

    @DgsData(parentType = USER.TYPE_NAME, field = USER.Posts)
    fun userPosts(dfe: DgsDataFetchingEnvironment): CompletableFuture<List<Post>> {
        val source = dfe.getSource<User>()
        val loader = dfe.getDataLoader<Long, List<Post>>(UserPostsBatchLoader::class.java)

        return loader.load(source.id.toLong())
    }

    @DgsData(parentType = TOPIC.TYPE_NAME, field = TOPIC.Posts)
    fun topicPosts(dfe: DgsDataFetchingEnvironment): CompletableFuture<List<Post>> {
        val topic = dfe.getSource<Topic>()
        val dataLoader = dfe.getDataLoader<Long, List<Post>>(TopicPostsBatchLoader::class.java)

        return dataLoader.load(topic.id.toLong())
    }
}
