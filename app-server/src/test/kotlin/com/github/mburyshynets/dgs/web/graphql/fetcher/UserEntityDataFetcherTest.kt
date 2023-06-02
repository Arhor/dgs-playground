//package com.github.mburyshynets.dgs.web.graphql.fetcher
//
//import com.github.mburyshynets.dgs.data.model.Setting
//import com.github.mburyshynets.dgs.graphql.generated.types.User
//import com.github.mburyshynets.dgs.service.UserService
//import com.netflix.graphql.dgs.DgsQueryExecutor
//import com.ninjasquad.springmockk.MockkBean
//import io.mockk.every
//import io.mockk.verify
//import org.assertj.core.api.Assertions.assertThat
//import org.junit.jupiter.api.Test
//import org.springframework.beans.factory.annotation.Autowired
//import java.util.EnumSet
//
//@DgsTest(classes = [UserFetcher::class])
////@ContextConfiguration(classes = [UserFetcher::class])
//internal class UserEntityDataFetcherTest {
//
//    @MockkBean
//    private lateinit var userService: UserService
//
//    @Autowired
//    private lateinit var dgsQueryExecutor: DgsQueryExecutor
//
//    @Test
//    fun `should return successful result containing empty list of users`() {
//        // given
//        every { userService.getAllUsers() } returns emptyList()
//
//        // when
//        val result = dgsQueryExecutor.execute(
//            """
//            query {
//                users {
//                    id
//                    username
//                    settings
//                }
//            }
//            """
//        )
//
//        // then
//        verify { userService.getAllUsers() }
//
//        assertThat(result.errors)
//            .isEmpty()
//        assertThat(result.isDataPresent)
//            .isTrue
//        assertThat(result.getData<Map<String, *>>())
//            .containsEntry("users", emptyList<Any>())
//    }
//
//    @Test
//    fun `should return successful result containing list with single expected user`() {
//        // given
//        val user = User(
//            id = 1,
//            username = "test-user",
//            settings = EnumSet.allOf(Setting::class.java),
//        )
//
//        every { userService.getAllUsers() } returns listOf(user)
//
//        // when
//        val result = dgsQueryExecutor.execute(
//            """
//            query {
//                users {
//                    id
//                    username
//                    settings
//                }
//            }
//            """
//        )
//
//        // then
//        verify { userService.getAllUsers() }
//
//        assertThat(result.errors)
//            .isEmpty()
//        assertThat(result.isDataPresent)
//            .isTrue
//        assertThat(result.getData<Map<Any, Any>>())
//            .containsEntry(
//                "users", listOf(
//                    mapOf(
//                        "id" to user.id,
//                        "username" to user.username,
//                        "settings" to user.settings?.map { it.name },
//                    )
//                )
//            )
//    }
//}
