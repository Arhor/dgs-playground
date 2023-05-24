package com.github.mburyshynets.dgs.service.impl

import com.github.mburyshynets.dgs.data.repository.UserRepository
import com.github.mburyshynets.dgs.graphql.generated.types.CreateUserRequest
import com.github.mburyshynets.dgs.graphql.generated.types.User
import com.github.mburyshynets.dgs.service.ExtendedUserDetails
import com.github.mburyshynets.dgs.service.UserService
import com.github.mburyshynets.dgs.service.mapper.UserMapper
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository,
    private val userMapper: UserMapper,
) : UserService, UserDetailsService {

    override fun loadUserByUsername(username: String): ExtendedUserDetails {
        return userRepository.findByUsername(username)
            ?.let {
                ExtendedUserDetails(
                    id = it.id,
                    username = it.username,
                    password = it.password,
                    authorities = listOf(SimpleGrantedAuthority("ROLE_USER"))
                )
            }
            ?: throw UsernameNotFoundException("InternalUser with username '$username' is not found.")
    }

    @Transactional
    override fun createNewUser(request: CreateUserRequest): User {
        return passwordEncoder.encode(request.password)
            .let { userMapper.mapToEntity(request, password = it) }
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
