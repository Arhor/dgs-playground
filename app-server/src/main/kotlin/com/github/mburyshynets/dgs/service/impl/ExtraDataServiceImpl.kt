package com.github.mburyshynets.dgs.service.impl

import com.github.mburyshynets.dgs.data.model.ExtraDataEntity
import com.github.mburyshynets.dgs.data.repository.ExtraDataRepository
import com.github.mburyshynets.dgs.graphql.generated.types.CreateExtraDataRequest
import com.github.mburyshynets.dgs.graphql.generated.types.ExtraData
import com.github.mburyshynets.dgs.service.ExtraDataLookupKey
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
        val extraDataEntity = ExtraDataEntity(
            entityId = request.entityId,
            entityType = request.entityType.name,
            propertyName = request.propertyName,
            propertyValue = request.propertyValue,
        )
        return extraDataRepository.save(extraDataEntity).let {
            ExtraData(
                id = it.id!!.toString(),
                entityId = it.entityId,
                entityType = it.entityType,
                propertyName = it.propertyName,
                propertyValue = it.propertyValue
            )
        }
    }


    @Transactional(readOnly = true)
    override fun getBatchExtraData(keys: Set<ExtraDataLookupKey>): Map<ExtraDataLookupKey, List<ExtraData>> {
        if (keys.isEmpty()) {
            return emptyMap()
        }

        val target = HashMap<ExtraDataLookupKey, List<ExtraData>>()

        for (key in keys) {
            target[key] =
                extraDataRepository.findAllEntityTypeAndByEntityId(key.type.name, key.id).map {
                    ExtraData(
                        it.id!!.toString(),
                        it.entityId,
                        it.entityType,
                        it.propertyName,
                        it.propertyValue,
                    )
                }
        }
        return target
    }
}
