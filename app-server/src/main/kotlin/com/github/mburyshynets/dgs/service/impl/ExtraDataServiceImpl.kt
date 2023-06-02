package com.github.mburyshynets.dgs.service.impl

import com.github.mburyshynets.dgs.data.model.ExtraDataEntity
import com.github.mburyshynets.dgs.data.repository.ExtraDataRepository
import com.github.mburyshynets.dgs.graphql.generated.types.CreateExtraDataRequest
import com.github.mburyshynets.dgs.graphql.generated.types.ExtendedEntityType
import com.github.mburyshynets.dgs.graphql.generated.types.ExtraData
import com.github.mburyshynets.dgs.service.ExtraDataService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ExtraDataServiceImpl @Autowired constructor(
    private val extraDataRepository: ExtraDataRepository,
) : ExtraDataService {

    @Transactional
    override fun createExtraData(request: CreateExtraDataRequest): ExtraData {
//        val extraDataEntity = ExtraDataEntity(
//            entityId = request.entityId.toLong(),
//            entityType = request.entityType.name,
//            fieldName = request.fieldName,
//            propertyValue = request.fieldValue,
//        )
//        return extraDataRepository.save(extraDataEntity).let {
//            ExtraData(
//                id = it.id!!.toString(),
//                entityId = it.entityId.toString(),
//                entityType = it.entityType,
//                fieldName = it.fieldName,
//                fieldValue = it.propertyValue,
//            )
//        }

        TODO()
    }

    @Transactional(readOnly = true)
    override fun getExtraDataInBatch(type: ExtendedEntityType, entityIds: Set<String>): Map<String, ExtraData> {
        if (entityIds.isEmpty()) {
            return emptyMap()
        }
        val allExtraData = extraDataRepository.findAllByEntityTypeAndEntityIdIn(type.name, entityIds)

        return allExtraData.associateBy(ExtraDataEntity::entityId) {
            ExtraData(
                it.id!!.toString(),
                it.entityId,
                it.entityType,
                it.data,
            )
        }
    }
}
