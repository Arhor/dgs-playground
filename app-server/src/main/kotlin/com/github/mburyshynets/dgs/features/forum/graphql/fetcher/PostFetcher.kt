package com.github.mburyshynets.dgs.features.forum.graphql.fetcher

import com.github.mburyshynets.dgs.features.forum.PostService
import com.github.mburyshynets.dgs.features.forum.graphql.loader.PostBatchLoader
import com.github.mburyshynets.dgs.features.user.ExtendedUserDetails
import com.github.mburyshynets.dgs.generated.graphql.DgsConstants.TOPIC
import com.github.mburyshynets.dgs.generated.graphql.DgsConstants.USER
import com.github.mburyshynets.dgs.generated.graphql.types.CreatePostRequest
import com.github.mburyshynets.dgs.generated.graphql.types.Indentifiable
import com.github.mburyshynets.dgs.generated.graphql.types.Post
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
        return dfe.loadPostsUsing<PostBatchLoader.ForUser>()
    }

    @DgsData(parentType = TOPIC.TYPE_NAME, field = TOPIC.Posts)
    fun topicPosts(dfe: DgsDataFetchingEnvironment): CompletableFuture<List<Post>> {
        return dfe.loadPostsUsing<PostBatchLoader.ForTopic>()
    }

    private inline fun <reified T> DgsDataFetchingEnvironment.loadPostsUsing(): CompletableFuture<List<Post>>
        where T : PostBatchLoader {

        val loader = getDataLoader<Long, List<Post>>(T::class.java)
        val source = getSource<Indentifiable>()

        return loader.load(source.id.toLong())
    }
}
