package com.github.mburyshynets.dgs.service

import com.github.mburyshynets.dgs.data.Setting
import com.github.mburyshynets.dgs.graphql.generated.types.UserDto
import java.util.EnumSet

interface UserService {

    fun createNewUser(username: String, settings: EnumSet<Setting>?): UserDto

    fun getUserByUsername(username: String): UserDto

    fun getAllUsers(): List<UserDto>
}
