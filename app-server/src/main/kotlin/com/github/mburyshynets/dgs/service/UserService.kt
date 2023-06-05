package com.github.mburyshynets.dgs.service

import com.github.mburyshynets.dgs.generated.graphql.types.CreateUserRequest
import com.github.mburyshynets.dgs.generated.graphql.types.User

interface UserService {

    fun createNewUser(request: CreateUserRequest): User

    fun getUserByUsername(username: String): User

    fun getAllUsers(offset: Offset, limit: Limit): List<User>
}
