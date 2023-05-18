package com.github.mburyshynets.dgs.service.impl

import com.github.mburyshynets.dgs.data.repository.UserRepository
import com.github.mburyshynets.dgs.graphql.generated.types.CreateUserRequest
import com.github.mburyshynets.dgs.graphql.generated.types.User
import com.github.mburyshynets.dgs.service.UserService
import com.github.mburyshynets.dgs.service.mapper.UserMapper
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper,
) : UserService {

    @Transactional
    override fun createNewUser(request: CreateUserRequest): User {
        return userMapper.mapToEntity(request)
            .let { userRepository.save(it) }
            .let { userMapper.mapToDTO(it) }
    }

    @Transactional(readOnly = true)
    override fun getUserByUsername(username: String): User {
        return userRepository.findByUsername(username)?.let { userMapper.mapToDTO(it) }
            ?: throw DgsEntityNotFoundException()
    }

    @Transactional(readOnly = true)
    override fun getAllUsers(): List<User> {
        return userRepository.findAll().map(userMapper::mapToDTO).toList()
    }
}
