package com.github.mburyshynets.dgs.data.model

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Immutable
import org.springframework.data.relational.core.mapping.Table

@Table("post_extensions")
@Immutable
data class PostExtension(
    @Id
    val id: Long? = null,
    val postId: Long?,
    val additionalContent: String,
)
