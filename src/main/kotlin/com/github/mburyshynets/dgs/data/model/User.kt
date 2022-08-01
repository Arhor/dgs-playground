package com.github.mburyshynets.dgs.data.model

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Immutable
import org.springframework.data.relational.core.mapping.Table

@Table("users")
@Immutable
data class User(
    @Id
    val id: Long,
    val username: String
)
