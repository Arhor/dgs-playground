package com.github.mburyshynets.dgs.graphql

import com.github.mburyshynets.dgs.graphql.generated.types.PostDto
import com.github.mburyshynets.dgs.service.PostService
import com.netflix.graphql.dgs.DgsDataLoader
import org.dataloader.MappedBatchLoader
import java.util.concurrent.CompletableFuture.supplyAsync
import java.util.concurrent.CompletionStage

@DgsDataLoader(name = "posts")
class PostDataLoader(private val postService: PostService) : MappedBatchLoader<Long, List<PostDto>> {

    override fun load(keys: Set<Long>): CompletionStage<Map<Long, List<PostDto>>> = supplyAsync {
        postService.getPostsByUserIds(keys)
    }
}
