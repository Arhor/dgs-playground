package com.github.mburyshynets.dgs.web.graphql.loader

import com.github.mburyshynets.dgs.graphql.generated.types.Post
import com.github.mburyshynets.dgs.service.PostService
import com.netflix.graphql.dgs.DgsDataLoader
import org.dataloader.MappedBatchLoader
import java.util.concurrent.CompletableFuture.supplyAsync
import java.util.concurrent.CompletionStage

@DgsDataLoader(name = "posts")
class PostsBatchLoader(private val postService: PostService) : MappedBatchLoader<Long, List<Post>> {

    override fun load(keys: Set<Long>): CompletionStage<Map<Long, List<Post>>> {
        return supplyAsync {
            postService.getPostsByUserIds(keys)
        }
    }
}
