package com.github.mburyshynets.dgs.service

import com.github.mburyshynets.dgs.graphql.generated.types.CreateExtraDataRequest
import com.github.mburyshynets.dgs.graphql.generated.types.ExtendedEntityType
import com.github.mburyshynets.dgs.graphql.generated.types.ExtraData

interface ExtraDataService {

    fun createExtraData(request: CreateExtraDataRequest): ExtraData

    fun getExtraDataInBatch(type: ExtendedEntityType, entityIds: Set<String>): Map<String, ExtraData>
}
