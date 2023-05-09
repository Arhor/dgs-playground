package com.github.mburyshynets.dgs.service

import com.github.mburyshynets.dgs.graphql.generated.types.CreateUserRequest
import com.github.mburyshynets.dgs.graphql.generated.types.UserDto

interface UserService {

    fun createNewUser(request: CreateUserRequest): UserDto

    fun getUserByUsername(username: String): UserDto

    fun getAllUsers(): List<UserDto>
}
