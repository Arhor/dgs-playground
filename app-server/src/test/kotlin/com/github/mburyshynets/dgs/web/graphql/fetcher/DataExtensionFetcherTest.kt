package com.github.mburyshynets.dgs.web.graphql.fetcher

import com.github.mburyshynets.dgs.graphql.generated.types.ExtraData
import com.github.mburyshynets.dgs.graphql.generated.types.Post
import com.github.mburyshynets.dgs.graphql.generated.types.User
import com.github.mburyshynets.dgs.service.DataExtensionLookupKey
import com.github.mburyshynets.dgs.service.DataExtensionService
import com.github.mburyshynets.dgs.service.PostService
import com.github.mburyshynets.dgs.service.UserService
import com.github.mburyshynets.dgs.web.graphql.loader.DataExtensionBatchLoader
import com.github.mburyshynets.dgs.web.graphql.loader.PostsBatchLoader
import com.netflix.graphql.dgs.DgsQueryExecutor
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.UUID
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong

@DgsTest(
    classes = [
        UserFetcher::class,
        PostFetcher::class,
        PostsBatchLoader::class,
        DataExtensionFetcher::class,
        DataExtensionBatchLoader::class,
    ]
)
internal class DataExtensionFetcherTest {

    @MockkBean
    private lateinit var userService: UserService

    @MockkBean
    private lateinit var postService: PostService

    @MockkBean
    private lateinit var dataExtensionService: DataExtensionService

    @Autowired
    private lateinit var dgsQueryExecutor: DgsQueryExecutor

    @Test
    fun `should return successful result containing empty list of user posts`() {
        // Given
        val postIdGenerator = AtomicLong(1)
        val users = (1..3).map { User(id = it.toLong(), username = "test-user-$it") }

        every { userService.getAllUsers() } returns users
        every { postService.getPostsByUserIds(any()) } answers {
            firstArg<Set<Long>>().groupBy({ it }, {
                Post(
                    id = postIdGenerator.getAndIncrement(),
                    userId = it,
                    content = "test-post",
                )
            })
        }
        val counter = AtomicInteger(0)

        every { dataExtensionService.getDataExtensions(any()) } answers {
            println("CALLED: ${counter.incrementAndGet()} - ${firstArg<Any>()}")

            firstArg<Set<DataExtensionLookupKey>>().groupBy({ it }, {
                ExtraData(
                    id = UUID.randomUUID().toString(),
                    entityId = it.id,
                    entityType = it.type.name,
                    propertyName = "${it.type} extra field ${it.id}",
                    propertyValue = "some text"
                )
            })
        }

        // when
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

        // then
        verify(exactly = 1) { userService.getAllUsers() }
        verify(exactly = 1) { postService.getPostsByUserIds(users.map { it.id }.toSet()) }

        println(result)

        assertThat(result.errors)
            .isEmpty()
        assertThat(result.isDataPresent)
            .isTrue
    }
}
