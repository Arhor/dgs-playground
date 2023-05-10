package com.github.mburyshynets.dgs.service

import com.github.mburyshynets.dgs.graphql.generated.types.CreateDataExtensionRequest
import com.github.mburyshynets.dgs.graphql.generated.types.DataExtension

interface DataExtensionService {

    fun createDataExtension(request: CreateDataExtensionRequest): DataExtension

    fun getPostsByPostIds(keys: Set<Long>): Map<Long, List<DataExtension>>
}
