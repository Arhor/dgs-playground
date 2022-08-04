package com.github.mburyshynets.dgs.service.impl

import com.github.mburyshynets.dgs.data.Settings
import com.github.mburyshynets.dgs.data.model.User
import com.github.mburyshynets.dgs.data.repository.UserRepository
import com.github.mburyshynets.dgs.graphql.generated.types.CreateUserRequest
import com.github.mburyshynets.dgs.graphql.generated.types.UserDto
import com.github.mburyshynets.dgs.service.UserService
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserServiceImpl(private val userRepository: UserRepository) : UserService {

    override fun createNewUser(request: CreateUserRequest): UserDto {
        return userRepository.save(
            User(
                username = request.username,
                settings = request.settings?.let(::Settings),
            )
        ).toDto()
    }

    @Transactional(readOnly = true)
    override fun getUserByUsername(username: String): UserDto {
        return userRepository.findByUsername(username)?.toDto()
            ?: throw DgsEntityNotFoundException()
    }

    @Transactional(readOnly = true)
    override fun getAllUsers(): List<UserDto> {
        return userRepository.findAll().map(User::toDto).toList()
    }
}
