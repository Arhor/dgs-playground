package com.github.mburyshynets.dgs.service.impl

import com.github.mburyshynets.dgs.data.model.DataExtensionEntity
import com.github.mburyshynets.dgs.data.repository.DataExtensionRepository
import com.github.mburyshynets.dgs.graphql.generated.types.CreateDataExtensionRequest
import com.github.mburyshynets.dgs.graphql.generated.types.DataExtension
import com.github.mburyshynets.dgs.service.DataExtensionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DataExtensionServiceImpl @Autowired constructor(
    private val dataExtensionRepository: DataExtensionRepository,
) : DataExtensionService {

    @Transactional
    override fun createDataExtension(request: CreateDataExtensionRequest): DataExtension {
        val dataExtensionEntity = DataExtensionEntity(
            entityId = request.entityId,
            entityType = request.entityType,
            propertyName = request.propertyName,
            propertyValue = request.propertyValue,
        )
        return dataExtensionRepository.save(dataExtensionEntity).let {
            DataExtension(
                id = it.id!!,
                entityId = it.entityId,
                entityType = it.entityType,
                propertyName = it.propertyName,
                propertyValue = it.propertyValue
            )
        }
    }

    @Transactional(readOnly = true)
    override fun getPostsByPostIds(keys: Set<Long>): Map<Long, List<DataExtension>> {
        TODO("Not yet implemented!!!")
    }
}
