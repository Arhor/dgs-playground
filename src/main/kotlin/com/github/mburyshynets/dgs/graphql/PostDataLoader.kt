package com.github.mburyshynets.dgs.graphql

import com.github.mburyshynets.dgs.data.repository.PostRepository
import com.github.mburyshynets.dgs.graphql.generated.types.PostDto
import com.netflix.graphql.dgs.DgsDataLoader
import org.dataloader.MappedBatchLoader
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage

@DgsDataLoader(name = "posts")
class PostDataLoader(
    private val postRepository: PostRepository,
) : MappedBatchLoader<Long, List<PostDto>> {

    override fun load(keys: Set<Long>): CompletionStage<Map<Long, List<PostDto>>> {
        return CompletableFuture.supplyAsync {
            postRepository
                .findAllByUserIdIn(keys)
                .map { it.toDto() }
                .groupBy { it.userId!! }
        }
    }
}
