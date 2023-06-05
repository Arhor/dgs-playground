package com.github.mburyshynets.dgs.data.repository

import com.github.mburyshynets.dgs.data.model.UserEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository

interface UserRepository : CrudRepository<UserEntity, Long>, PagingAndSortingRepository<UserEntity, Long> {

    fun findByUsername(username: String): UserEntity?
}
