package com.github.mburyshynets.dgs.web.graphql.loader

import com.github.mburyshynets.dgs.graphql.generated.types.ExtraData
import com.github.mburyshynets.dgs.service.ExtraDataService
import com.github.mburyshynets.dgs.service.ExtraDataLookupKey
import com.netflix.graphql.dgs.DgsDataLoader
import org.dataloader.MappedBatchLoader
import java.util.concurrent.CompletableFuture.supplyAsync
import java.util.concurrent.CompletionStage

private typealias ExtensionLoader = MappedBatchLoader<ExtraDataLookupKey, List<ExtraData>>

@DgsDataLoader(name = "extensions")
class ExtraDataBatchLoader(private val service: ExtraDataService) : ExtensionLoader {

    override fun load(keys: Set<ExtraDataLookupKey>): CompletionStage<Map<ExtraDataLookupKey, List<ExtraData>>> {
        return supplyAsync {
            service.getBatchExtraData(keys)
        }
    }
}
