package com.github.mburyshynets.dgs.web.graphql.fetcher

import com.github.mburyshynets.dgs.graphql.generated.types.CreateUserRequest
import com.github.mburyshynets.dgs.graphql.generated.types.User
import com.github.mburyshynets.dgs.service.UserService
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument

@DgsComponent
class UserFetcher(private val userService: UserService) {

    @DgsQuery
    fun users(): List<User> {
        return userService.getAllUsers()
    }

    @DgsQuery
    fun user(@InputArgument username: String): User {
        return userService.getUserByUsername(username)
    }

    @DgsMutation
    fun createUser(@InputArgument request: CreateUserRequest): User {
        return userService.createNewUser(request)
    }
}
