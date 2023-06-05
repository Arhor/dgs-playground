package com.github.mburyshynets.dgs.web.graphql.fetcher

import com.github.mburyshynets.dgs.graphql.generated.types.CreateUserRequest
import com.github.mburyshynets.dgs.graphql.generated.types.User
import com.github.mburyshynets.dgs.service.ExtendedUserDetails
import com.github.mburyshynets.dgs.service.UserService
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import org.springframework.security.core.annotation.AuthenticationPrincipal

@DgsComponent
class UserFetcher(private val userService: UserService) {

    @DgsMutation
    fun createUser(@InputArgument request: CreateUserRequest): User =
        userService.createNewUser(request)

    @DgsQuery
    fun users(): List<User> =
        userService.getAllUsers()

    @DgsQuery
    fun user(@InputArgument username: String): User =
        userService.getUserByUsername(username)

    @DgsQuery
    fun currentUser(@AuthenticationPrincipal currentUser: ExtendedUserDetails?): User? =
        currentUser?.let {
            User(
                id = it.id.toString(),
                username = it.username,
            )
        }
}
