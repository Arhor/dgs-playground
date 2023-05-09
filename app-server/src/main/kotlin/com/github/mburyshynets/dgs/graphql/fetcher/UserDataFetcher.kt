package com.github.mburyshynets.dgs.graphql.fetcher

import com.github.mburyshynets.dgs.graphql.generated.types.CreateUserRequest
import com.github.mburyshynets.dgs.graphql.generated.types.UserDto
import com.github.mburyshynets.dgs.service.UserService
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import org.slf4j.LoggerFactory

@DgsComponent
class UserDataFetcher(private val userService: UserService) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @DgsQuery
    fun users(): List<UserDto> {
        logger.info("resolving users")
        return userService.getAllUsers()
    }

    @DgsQuery
    fun user(@InputArgument username: String): UserDto {
        return userService.getUserByUsername(username)
    }

    @DgsMutation
    fun createUser(@InputArgument request: CreateUserRequest): UserDto {
        return userService.createNewUser(request)
    }
}
