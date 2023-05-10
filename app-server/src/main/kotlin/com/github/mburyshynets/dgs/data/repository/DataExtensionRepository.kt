package com.github.mburyshynets.dgs.data.repository

import com.github.mburyshynets.dgs.data.model.DataExtensionEntity
import org.springframework.data.repository.CrudRepository

interface DataExtensionRepository : CrudRepository<DataExtensionEntity, Long> {

    fun findAllByEntityIdAndEntityType(entityId: Long, entityType: String): List<DataExtensionEntity>
}
