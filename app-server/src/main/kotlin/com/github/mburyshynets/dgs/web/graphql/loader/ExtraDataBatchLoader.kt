package com.github.mburyshynets.dgs.web.graphql.loader

import com.github.mburyshynets.dgs.async
import com.github.mburyshynets.dgs.graphql.generated.types.ExtraData
import com.github.mburyshynets.dgs.service.ExtraDataLookupKey
import com.github.mburyshynets.dgs.service.ExtraDataService
import com.netflix.graphql.dgs.DgsDataLoader
import org.dataloader.MappedBatchLoader
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executor

@DgsDataLoader(name = "extensions")
class ExtraDataBatchLoader(
    private val asyncExecutor: Executor,
    private val service: ExtraDataService,
) : MappedBatchLoader<ExtraDataLookupKey, List<ExtraData>> {

    override fun load(keys: Set<ExtraDataLookupKey>): CompletableFuture<Map<ExtraDataLookupKey, List<ExtraData>>> {
        return asyncExecutor.async { service.getBatchExtraData(keys) }
    }
}
