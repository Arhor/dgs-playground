package com.github.mburyshynets.dgs.service

import com.github.mburyshynets.dgs.graphql.generated.types.CreateUserRequest
import com.github.mburyshynets.dgs.graphql.generated.types.User

interface UserService {

    fun createNewUser(request: CreateUserRequest): User

    fun getUserByUsername(username: String): User

    fun getAllUsers(): List<User>
}
