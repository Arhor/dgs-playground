package com.github.mburyshynets.dgs.service

import com.github.mburyshynets.dgs.graphql.generated.types.UserDto

interface UserService {

    fun createNewUser(username: String): UserDto

    fun getUserByUsername(username: String): UserDto

    fun getAllUsers(): List<UserDto>
}
