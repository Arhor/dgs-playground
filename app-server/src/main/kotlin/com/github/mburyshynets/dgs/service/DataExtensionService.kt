package com.github.mburyshynets.dgs.service

import com.github.mburyshynets.dgs.graphql.generated.types.CreateExtraDataRequest
import com.github.mburyshynets.dgs.graphql.generated.types.ExtraData

interface DataExtensionService {

    fun createDataExtension(request: CreateExtraDataRequest): ExtraData

    fun getDataExtensions(keys: Set<DataExtensionLookupKey>): Map<DataExtensionLookupKey, List<ExtraData>>
}
