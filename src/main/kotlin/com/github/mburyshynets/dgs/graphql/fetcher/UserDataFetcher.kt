package com.github.mburyshynets.dgs.graphql.fetcher

import com.github.mburyshynets.dgs.data.Setting
import com.github.mburyshynets.dgs.graphql.generated.types.UserDto
import com.github.mburyshynets.dgs.service.UserService
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import java.util.EnumSet

@DgsComponent
class UserDataFetcher(private val userService: UserService) {

    @DgsQuery
    fun users(): List<UserDto> {
        return userService.getAllUsers()
    }

    @DgsQuery
    fun user(@InputArgument username: String): UserDto {
        return userService.getUserByUsername(username)
    }

    @DgsMutation
    fun createUser(@InputArgument username: String, @InputArgument settings: EnumSet<Setting>?): UserDto {
        return userService.createNewUser(username, settings)
    }
}
