package com.github.mburyshynets.dgs.features.extradata

import com.github.mburyshynets.dgs.generated.graphql.types.CreateExtraDataRequest
import com.github.mburyshynets.dgs.generated.graphql.types.ExtendedEntityType
import com.github.mburyshynets.dgs.generated.graphql.types.ExtraData

interface ExtraDataService {

    fun createExtraData(request: CreateExtraDataRequest): ExtraData

    fun getExtraDataInBatch(type: ExtendedEntityType, entityIds: Set<String>): Map<String, ExtraData>
}
