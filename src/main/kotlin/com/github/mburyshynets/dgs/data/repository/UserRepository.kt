package com.github.mburyshynets.dgs.data.repository

import com.github.mburyshynets.dgs.data.model.User
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Long> {

    fun findByUsername(username: String): User?
}
