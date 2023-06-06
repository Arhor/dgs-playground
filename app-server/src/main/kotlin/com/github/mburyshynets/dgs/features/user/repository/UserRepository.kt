package com.github.mburyshynets.dgs.features.user.repository

import com.github.mburyshynets.dgs.features.user.entity.UserEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository

interface UserRepository : CrudRepository<UserEntity, Long>, PagingAndSortingRepository<UserEntity, Long> {

    fun findByUsername(username: String): UserEntity?
}
