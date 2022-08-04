package com.github.mburyshynets.dgs

import com.github.mburyshynets.dgs.data.Setting
import com.github.mburyshynets.dgs.data.Settings
import com.github.mburyshynets.dgs.data.model.User
import com.github.mburyshynets.dgs.data.repository.UserRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.transaction.annotation.Transactional
import java.util.EnumSet

@SpringBootTest
@AutoConfigureMockMvc
internal class ApplicationIntegrationTest : WithDatabaseContainer {

    @Autowired
    private lateinit var http: MockMvc

    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    @Transactional
    fun `should return response with expected structure for the 'users' query`() {
        // given
        val user = prepareUser()

        // when
        val result = http.post("/graphql") {
            contentType = MediaType.APPLICATION_JSON
            content = """{ "query": "${usersQuery()}" }"""
        }

        // then
        result.andDo { print() }.andExpect {
            status { isOk() }
            jsonPath("$.data") { exists(); isMap() }
            jsonPath("$.data.users") { exists(); isArray() }
            jsonPath("$.data.users.length()") { value(1) }
            jsonPath("$.data.users[0].id") { value(user.id) }
            jsonPath("$.data.users[0].username") { value(user.username) }
            jsonPath("$.data.users[0].settings") { value(user.settings?.items?.map { it.name }) }
        }
    }

    @Test
    @Transactional
    fun `should return response with expected structure for the 'user' query using existing user username`() {
        // given
        val user = prepareUser()

        // when
        val result = http.post("/graphql") {
            contentType = MediaType.APPLICATION_JSON
            content = """{ "query": "${userQuery(user.username)}" }"""
        }

        // then
        result.andDo { print() }.andExpect {
            status { isOk() }
            jsonPath("$.data") { exists(); isMap() }
            jsonPath("$.data.user") { exists(); isMap() }
            jsonPath("$.data.user.id") { value(user.id) }
            jsonPath("$.data.user.username") { value(user.username) }
            jsonPath("$.data.user.settings") { value(user.settings?.items?.map { it.name }) }
        }
    }

    private fun usersQuery(): String = """
        query {
            users {
                id
                username
                settings
            }
        }
    """.compact()

    private fun userQuery(username: String): String = """
        query {
            user(username: \"$username\") {
                id
                username
                settings
            }
        }
    """.compact()

    private fun prepareUser() = userRepository.save(
        User(
            username = "test-user",
            settings = Settings(
                items = EnumSet.of(
                    Setting.SETTING_1,
                    Setting.SETTING_2,
                    Setting.SETTING_3,
                )
            )
        )
    )

    companion object {
        private val LINE_BREAKS = Regex("[\r\n]")
        private val WHITESPACES = Regex("( )+")

        fun String.compact(): String = replace(LINE_BREAKS, "").replace(WHITESPACES, " ").trim()
    }
}
