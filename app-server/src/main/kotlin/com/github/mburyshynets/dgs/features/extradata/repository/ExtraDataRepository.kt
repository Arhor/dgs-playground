package com.github.mburyshynets.dgs.features.extradata.repository

import com.github.mburyshynets.dgs.features.extradata.entity.ExtraDataEntity
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface ExtraDataRepository : CrudRepository<ExtraDataEntity, UUID> {

    fun findAllByEntityTypeAndEntityIdIn(entityType: String, entityIds: Collection<String>): List<ExtraDataEntity>
}
