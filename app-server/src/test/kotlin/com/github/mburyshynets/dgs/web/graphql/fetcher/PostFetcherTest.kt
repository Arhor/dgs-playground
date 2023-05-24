package com.github.mburyshynets.dgs.web.graphql.fetcher

import com.github.mburyshynets.dgs.config.ConfigureAsyncTasks
import com.github.mburyshynets.dgs.graphql.generated.types.Post
import com.github.mburyshynets.dgs.graphql.generated.types.User
import com.github.mburyshynets.dgs.service.PostService
import com.github.mburyshynets.dgs.service.UserService
import com.github.mburyshynets.dgs.web.graphql.loader.PostsBatchLoader
import com.netflix.graphql.dgs.DgsQueryExecutor
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@DgsTest(classes = [UserFetcher::class, PostFetcher::class, PostsBatchLoader::class, ConfigureAsyncTasks::class])
internal class PostFetcherTest {

    @MockkBean
    private lateinit var postService: PostService

    @MockkBean
    private lateinit var userService: UserService

    @Autowired
    private lateinit var dgsQueryExecutor: DgsQueryExecutor

    @Test
    fun `should return successful result containing empty list of user posts`() {
        // given
        val user = User(id = 1, username = "test-user")
        val post = Post(id = 2, userId = user.id, topicId = 1L, content = "test-post")

        every { userService.getUserByUsername(any()) } returns user
        every { postService.getPostsUserId(any()) } returns listOf(post)

        // when
        val result = dgsQueryExecutor.execute(
            """
            query {
                user(username: "${user.username}") {
                    posts {
                        id
                        userId
                        content
                    }
                }
            }
            """
        )

        // then
        verify { userService.getUserByUsername(user.username) }
        verify { postService.getPostsUserId(user.id) }

        assertThat(result.errors)
            .isEmpty()
        assertThat(result.isDataPresent)
            .isTrue
        assertThat(result.getData<Map<Any, Any>>())
            .containsEntry(
                "user", mapOf(
                    "posts" to listOf(
                        mapOf(
                            "id" to post.id,
                            "userId" to post.userId,
                            "content" to post.content
                        )
                    )
                )
            )
    }
}
