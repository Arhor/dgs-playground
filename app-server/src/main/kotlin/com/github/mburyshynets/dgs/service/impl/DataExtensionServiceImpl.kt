package com.github.mburyshynets.dgs.service.impl

import com.github.mburyshynets.dgs.data.model.ExtraDataEntity
import com.github.mburyshynets.dgs.data.repository.ExtraDataRepository
import com.github.mburyshynets.dgs.graphql.generated.types.CreateExtraDataRequest
import com.github.mburyshynets.dgs.graphql.generated.types.ExtraData
import com.github.mburyshynets.dgs.service.DataExtensionLookupKey
import com.github.mburyshynets.dgs.service.DataExtensionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DataExtensionServiceImpl @Autowired constructor(
    private val extraDataRepository: ExtraDataRepository,
) : DataExtensionService {

    @Transactional
    override fun createDataExtension(request: CreateExtraDataRequest): ExtraData {
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
    override fun getDataExtensions(keys: Set<DataExtensionLookupKey>): Map<DataExtensionLookupKey, List<ExtraData>> {


        val target = HashMap<DataExtensionLookupKey, List<ExtraData>>()

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
