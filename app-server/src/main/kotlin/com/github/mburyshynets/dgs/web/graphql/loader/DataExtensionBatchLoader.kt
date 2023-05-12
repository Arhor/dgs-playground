package com.github.mburyshynets.dgs.web.graphql.loader

import com.github.mburyshynets.dgs.graphql.generated.types.ExtraData
import com.github.mburyshynets.dgs.service.DataExtensionService
import com.github.mburyshynets.dgs.service.DataExtensionLookupKey
import com.netflix.graphql.dgs.DgsDataLoader
import org.dataloader.MappedBatchLoader
import java.util.concurrent.CompletableFuture.supplyAsync
import java.util.concurrent.CompletionStage

private typealias ExtensionLoader = MappedBatchLoader<DataExtensionLookupKey, List<ExtraData>>

@DgsDataLoader(name = "extensions")
class DataExtensionBatchLoader(private val service: DataExtensionService) : ExtensionLoader {

    override fun load(keys: Set<DataExtensionLookupKey>): CompletionStage<Map<DataExtensionLookupKey, List<ExtraData>>> {
        return supplyAsync {
            service.getDataExtensions(keys)
        }
    }
}
