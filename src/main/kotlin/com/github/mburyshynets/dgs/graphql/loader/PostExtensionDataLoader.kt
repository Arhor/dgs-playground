package com.github.mburyshynets.dgs.graphql.loader

import com.github.mburyshynets.dgs.graphql.generated.types.PostExtensionDto
import com.github.mburyshynets.dgs.service.PostExtensionService
import com.netflix.graphql.dgs.DgsDataLoader
import org.dataloader.MappedBatchLoader
import java.util.concurrent.CompletableFuture.supplyAsync
import java.util.concurrent.CompletionStage

@DgsDataLoader(name = "postExtensions")
class PostExtensionDataLoader(
    private val postExtensionService: PostExtensionService,
) : MappedBatchLoader<Long, List<PostExtensionDto>> {

    override fun load(keys: Set<Long>): CompletionStage<Map<Long, List<PostExtensionDto>>> {

        return supplyAsync {
            postExtensionService.getPostsByPostIds(keys)
        }
    }
}
