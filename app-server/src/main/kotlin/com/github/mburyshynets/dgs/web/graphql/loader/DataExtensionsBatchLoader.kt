package com.github.mburyshynets.dgs.web.graphql.loader

import com.github.mburyshynets.dgs.graphql.generated.types.DataExtension
import com.github.mburyshynets.dgs.service.DataExtensionService
import com.netflix.graphql.dgs.DgsDataLoader
import org.dataloader.MappedBatchLoader
import java.util.concurrent.CompletableFuture.supplyAsync
import java.util.concurrent.CompletionStage

@DgsDataLoader(name = "extensions")
class DataExtensionsBatchLoader(
    private val dataExtensionService: DataExtensionService,
) : MappedBatchLoader<Long, List<DataExtension>> {

    override fun load(keys: Set<Long>): CompletionStage<Map<Long, List<DataExtension>>> {
        return supplyAsync {
            dataExtensionService.getPostsByPostIds(keys)
        }
    }
}
