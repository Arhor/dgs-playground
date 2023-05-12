package com.github.mburyshynets.dgs.web.graphql.fetcher

import com.github.mburyshynets.dgs.graphql.generated.types.ExtraData
import com.github.mburyshynets.dgs.graphql.generated.types.Post
import com.github.mburyshynets.dgs.graphql.generated.types.User
import com.github.mburyshynets.dgs.service.ExtraDataLookupKey
import com.github.mburyshynets.dgs.service.ExtraDataService
import com.github.mburyshynets.dgs.service.PostService
import com.github.mburyshynets.dgs.service.UserService
import com.github.mburyshynets.dgs.web.graphql.loader.ExtraDataBatchLoader
import com.github.mburyshynets.dgs.web.graphql.loader.PostsBatchLoader
import com.netflix.graphql.dgs.DgsQueryExecutor
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.UUID
import java.util.concurrent.CompletableFuture
import java.util.concurrent.atomic.AtomicLong

@DgsTest(
    classes = [
        UserFetcher::class,
        PostFetcher::class,
        ExtraDataFetcher::class,
    ]
)
@MockkBean(
    classes = [
        PostService::class,
        ExtraDataService::class,
    ]
)
internal class ExtraDataFetcherTest {

    @MockkBean
    private lateinit var userService: UserService

    @MockkBean
    private lateinit var postsBatchLoader: PostsBatchLoader

    @MockkBean
    private lateinit var extraDataBatchLoader: ExtraDataBatchLoader

    @Autowired
    private lateinit var dgsQueryExecutor: DgsQueryExecutor

    @Test
    @Suppress("GraphQLUnresolvedReference")
    fun `should return successful result containing empty list of user posts`() {
        // Given
        val postIdGenerator = AtomicLong(1)
        val users = (1..3).map { User(id = it.toLong(), username = "test-user-$it") }

        every { userService.getAllUsers() } returns users
        every { postsBatchLoader.load(any()) } answers {
            CompletableFuture.completedFuture(
                firstArg<Set<Long>>().groupBy({ it }, {
                    Post(
                        id = postIdGenerator.getAndIncrement(),
                        userId = it,
                        content = "test-post",
                    )
                })
            )
        }

        every { extraDataBatchLoader.load(any()) } answers {
            CompletableFuture.completedFuture(
                firstArg<Set<ExtraDataLookupKey>>().groupBy({ it }, {
                    ExtraData(
                        id = UUID.randomUUID().toString(),
                        entityId = it.id,
                        entityType = it.type.name,
                        propertyName = "${it.type} extra field ${it.id}",
                        propertyValue = "some text"
                    )
                })
            )
        }

        // When
        val result = dgsQueryExecutor.execute(
            """
                query GetUsers {
                    users {
                        id
                        username
                        extraData {
                            ...extraDataFields
                        }
                        posts {
                            id
                            content
                            extraData {
                                ...extraDataFields
                            }
                        }
                    }
                }
                
                fragment extraDataFields on ExtraData {
                    id
                    entityId
                    entityType
                    propertyName
                    propertyValue
                }
            """
        )

        // Then
        verify { userService.getAllUsers() }
        verify { postsBatchLoader.load(any()) }
        verify { extraDataBatchLoader.load(any()) }

        assertThat(result.errors)
            .isEmpty()
        assertThat(result.isDataPresent)
            .isTrue
    }
}
