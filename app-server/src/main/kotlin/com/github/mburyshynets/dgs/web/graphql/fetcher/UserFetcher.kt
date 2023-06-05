package com.github.mburyshynets.dgs.web.graphql.fetcher

import com.github.mburyshynets.dgs.graphql.generated.types.CreateUserRequest
import com.github.mburyshynets.dgs.graphql.generated.types.User
import com.github.mburyshynets.dgs.service.ExtendedUserDetails
import com.github.mburyshynets.dgs.service.UserService
import com.github.mburyshynets.dgs.service.impl.Limit
import com.github.mburyshynets.dgs.service.impl.Offset
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import org.springframework.security.core.annotation.AuthenticationPrincipal

@DgsComponent
class UserFetcher(private val userService: UserService) {

    @DgsMutation
    fun createUser(@InputArgument request: CreateUserRequest): User {
        return userService.createNewUser(request)
    }

    @DgsQuery
    fun users(@InputArgument offset: Int, @InputArgument limit: Int): List<User> {
        return userService.getAllUsers(Offset.of(offset), Limit.of(limit))
    }

    @DgsQuery
    fun user(@InputArgument username: String): User {
        return userService.getUserByUsername(username)
    }

    @DgsQuery
    fun currentUser(@AuthenticationPrincipal currentUser: ExtendedUserDetails?): User? {
        return currentUser?.let {
            User(
                id = it.id.toString(),
                username = it.username,
            )
        }
    }
}
