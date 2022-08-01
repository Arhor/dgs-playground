package com.github.mburyshynets.dgs.graphql

import com.github.mburyshynets.dgs.data.model.User
import com.github.mburyshynets.dgs.data.repository.PostRepository
import com.github.mburyshynets.dgs.data.repository.UserRepository
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument

@DgsComponent
class UserDataFetcher(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
) {

    @DgsQuery
    fun user(@InputArgument username: String): User? {
        return userRepository.findByUsername(username)
    }

    @DgsQuery
    fun users(): List<User> {
        return userRepository.findAll().toList()
    }

    @DgsMutation
    fun createUser(@InputArgument username: String): User {
        return userRepository.save(User(username = username))
    }
}
