package com.github.mburyshynets.dgs.data.model

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Immutable
import org.springframework.data.relational.core.mapping.Table
import java.util.UUID

@Table("extra_data")
@Immutable
data class ExtraDataEntity(
    @Id
    val id: UUID? = null,
    val entityId: Long,
    val entityType: String,
    val propertyName: String,
    val propertyValue: String?,
)
