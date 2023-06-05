package com.github.mburyshynets.dgs.features.extradata.graphql.fetcher

import com.github.mburyshynets.dgs.generated.graphql.DgsConstants.POST
import com.github.mburyshynets.dgs.generated.graphql.DgsConstants.USER
import com.github.mburyshynets.dgs.generated.graphql.types.CreateExtraDataRequest
import com.github.mburyshynets.dgs.generated.graphql.types.ExtraData
import com.github.mburyshynets.dgs.generated.graphql.types.Indentifiable
import com.github.mburyshynets.dgs.features.extradata.ExtraDataService
import com.github.mburyshynets.dgs.features.extradata.graphql.loader.ExtraDataBatchLoader
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.InputArgument
import java.util.concurrent.CompletableFuture

@DgsComponent
class ExtraDataFetcher(private val extraDataService: ExtraDataService) {

    @DgsMutation
    fun createExtraData(@InputArgument request: CreateExtraDataRequest): ExtraData {
        return extraDataService.createExtraData(request)
    }

    @DgsData(parentType = USER.TYPE_NAME, field = USER.ExtraData)
    fun userExtraData(dfe: DgsDataFetchingEnvironment): CompletableFuture<ExtraData> {
        return dfe.loadExtraDataUsing<ExtraDataBatchLoader.ForUser>()
    }

    @DgsData(parentType = POST.TYPE_NAME, field = POST.ExtraData)
    fun postExtraData(dfe: DgsDataFetchingEnvironment): CompletableFuture<ExtraData> {
        return dfe.loadExtraDataUsing<ExtraDataBatchLoader.ForPost>()
    }

    private inline fun <reified T> DgsDataFetchingEnvironment.loadExtraDataUsing(): CompletableFuture<ExtraData>
        where T : ExtraDataBatchLoader {

        val loader = getDataLoader<String, ExtraData>(T::class.java)
        val source = getSource<Indentifiable>()

        return loader.load(source.id)
    }
}
