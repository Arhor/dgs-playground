package com.github.mburyshynets.dgs.data.model

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Immutable
import org.springframework.data.relational.core.mapping.Table

@Table("data_extensions")
@Immutable
data class DataExtensionEntity(
    @Id
    val id: Long? = null,
    val entityId: Long,
    val entityType: String,
    val propertyName: String,
    val propertyValue: String?,
)
