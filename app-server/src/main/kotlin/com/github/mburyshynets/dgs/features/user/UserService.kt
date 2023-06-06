package com.github.mburyshynets.dgs.features.user

import com.github.mburyshynets.dgs.generated.graphql.types.CreateUserRequest
import com.github.mburyshynets.dgs.generated.graphql.types.User
import com.github.mburyshynets.dgs.common.Limit
import com.github.mburyshynets.dgs.common.Offset

interface UserService {

    fun createNewUser(request: CreateUserRequest): User

    fun getUserByUsername(username: String): User

    fun getAllUsers(offset: Offset, limit: Limit): List<User>
}
