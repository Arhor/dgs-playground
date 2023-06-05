package com.github.mburyshynets.dgs.service.impl

import com.github.mburyshynets.dgs.data.repository.UserRepository
import com.github.mburyshynets.dgs.generated.graphql.types.CreateUserRequest
import com.github.mburyshynets.dgs.generated.graphql.types.User
import com.github.mburyshynets.dgs.service.ExtendedUserDetails.Companion.extendWith
import com.github.mburyshynets.dgs.service.Limit
import com.github.mburyshynets.dgs.service.Offset
import com.github.mburyshynets.dgs.service.OffsetBasedPageRequest
import com.github.mburyshynets.dgs.service.UserService
import com.github.mburyshynets.dgs.service.mapper.UserMapper
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.security.core.userdetails.User as SpringUserDetails


@Service
class UserServiceImpl(
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository,
    private val userMapper: UserMapper,
) : UserService, UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findByUsername(username)
            ?.let {
                SpringUserDetails.builder()
                    .username(it.username)
                    .password(it.password)
                    .roles("USER")
                    .build()
                    .extendWith(id = it.id)
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
    override fun getAllUsers(offset: Offset, limit: Limit): List<User> {
        return userRepository
            .findAll(OffsetBasedPageRequest(offset, limit))
            .map(userMapper::mapToDTO)
            .toList()
    }
}
