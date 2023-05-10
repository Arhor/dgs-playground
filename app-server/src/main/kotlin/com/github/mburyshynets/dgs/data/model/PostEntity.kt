package com.github.mburyshynets.dgs.data.model

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Immutable
import org.springframework.data.relational.core.mapping.Table

@Table("posts")
@Immutable
data class PostEntity(
    @Id
    val id: Long? = null,
    val userId: Long?,
    val content: String,
)
