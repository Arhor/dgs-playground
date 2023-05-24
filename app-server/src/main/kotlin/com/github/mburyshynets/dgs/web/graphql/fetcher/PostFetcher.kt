package com.github.mburyshynets.dgs.web.graphql.fetcher

import com.github.mburyshynets.dgs.graphql.generated.DgsConstants.TOPIC
import com.github.mburyshynets.dgs.graphql.generated.DgsConstants.USER
import com.github.mburyshynets.dgs.graphql.generated.types.CreatePostRequest
import com.github.mburyshynets.dgs.graphql.generated.types.Post
import com.github.mburyshynets.dgs.graphql.generated.types.Topic
import com.github.mburyshynets.dgs.graphql.generated.types.User
import com.github.mburyshynets.dgs.service.PostService
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.InputArgument
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails

@DgsComponent
class PostFetcher(private val postService: PostService) {

    // TODO: create loader
    @DgsData(parentType = USER.TYPE_NAME, field = USER.Posts)
    fun userPosts(dfe: DgsDataFetchingEnvironment): List<Post> {
        val user = dfe.getSource<User>()
        return postService.getPostsUserId(user.id)
    }

    // TODO: create loader
    @DgsData(parentType = TOPIC.TYPE_NAME, field = TOPIC.Posts)
    fun topicPosts(dfe: DgsDataFetchingEnvironment): List<Post> {
        val topic = dfe.getSource<Topic>()
        return postService.getPostsByTopicId(topic.id)
    }

    // TODO: create loader
    @DgsData(parentType = TOPIC.TYPE_NAME, field = TOPIC.LastPost)
    fun topicLastPost(dfe: DgsDataFetchingEnvironment): Post? {
        val topic = dfe.getSource<Topic>()
        return postService.getPostsByTopicId(topic.id).lastOrNull()
    }

    @DgsMutation
    @PreAuthorize("isAuthenticated()")
    fun createPost(
        @InputArgument request: CreatePostRequest,
        @AuthenticationPrincipal currentUser: UserDetails,
    ): Post {
        return postService.createNewPost(request, currentUser)
    }
}
