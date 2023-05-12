package com.github.mburyshynets.dgs.service

import com.github.mburyshynets.dgs.graphql.generated.types.CreateDataExtensionRequest
import com.github.mburyshynets.dgs.graphql.generated.types.DataExtension

interface DataExtensionService {

    fun createDataExtension(request: CreateDataExtensionRequest): DataExtension

    fun getDataExtensions(keys: Set<DataExtensionLookupKey>): Map<DataExtensionLookupKey, List<DataExtension>>
}
