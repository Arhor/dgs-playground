package com.github.mburyshynets.dgs.web.graphql.fetcher

import com.github.mburyshynets.dgs.graphql.generated.DgsConstants
import com.github.mburyshynets.dgs.graphql.generated.types.CreateDataExtensionRequest
import com.github.mburyshynets.dgs.graphql.generated.types.DataExtension
import com.github.mburyshynets.dgs.graphql.generated.types.Post
import com.github.mburyshynets.dgs.service.DataExtensionService
import com.github.mburyshynets.dgs.web.graphql.loader.DataExtensionsBatchLoader
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.InputArgument
import java.util.concurrent.CompletableFuture

@DgsComponent
class DataExtensionFetcher(private val dataExtensionService: DataExtensionService) {

    @DgsData.List(
        DgsData(parentType = DgsConstants.USER.TYPE_NAME, field = "extensions"),
        DgsData(parentType = DgsConstants.POST.TYPE_NAME, field = "extensions"),
    )
    fun extensions(dfe: DgsDataFetchingEnvironment): CompletableFuture<List<DataExtension>> {
        val dataLoader = dfe.getDataLoader<Long, List<DataExtension>>(DataExtensionsBatchLoader::class.java)
        val source = dfe.getSource<Post>()//TODO: fix

        return dataLoader.load(source.id)
    }

    @DgsMutation
    fun createDataExtension(@InputArgument request: CreateDataExtensionRequest): DataExtension {
        return dataExtensionService.createDataExtension(request)
    }
}
