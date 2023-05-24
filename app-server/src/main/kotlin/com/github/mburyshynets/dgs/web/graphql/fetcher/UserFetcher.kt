package com.github.mburyshynets.dgs.web.graphql.fetcher

import com.github.mburyshynets.dgs.graphql.generated.types.CreateUserRequest
import com.github.mburyshynets.dgs.graphql.generated.types.User
import com.github.mburyshynets.dgs.service.UserService
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails

@DgsComponent
class UserFetcher(private val userService: UserService) {

    @DgsQuery
    @PreAuthorize("isAuthenticated()")
    fun users(): List<User> {
        return userService.getAllUsers()
    }

    @DgsQuery
    @PreAuthorize("isAuthenticated()")
    fun user(@InputArgument username: String): User {
        return userService.getUserByUsername(username)
    }

    @DgsQuery
    fun currentUser(@AuthenticationPrincipal currentUser: UserDetails?): User? {
        return currentUser?.let {
            User(
                id = -1,
                username = it.username,
            )
        }
    }

    @DgsMutation
    fun createUser(@InputArgument request: CreateUserRequest): User {
        return userService.createNewUser(request)
    }
}
