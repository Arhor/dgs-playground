package com.github.mburyshynets.dgs.data.repository

import com.github.mburyshynets.dgs.data.model.UserEntity
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<UserEntity, Long> {

    fun findByUsername(username: String): UserEntity?
}
