package com.github.mburyshynets.dgs.data.repository

import com.github.mburyshynets.dgs.data.model.DataExtensionEntity
import org.springframework.data.repository.CrudRepository

interface DataExtensionRepository : CrudRepository<DataExtensionEntity, Long> {

    fun findAllEntityTypeAndByEntityId(entityType: String, entityId: Long): List<DataExtensionEntity>
}
