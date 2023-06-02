package com.github.mburyshynets.dgs.web.graphql.loader

import com.github.mburyshynets.dgs.graphql.generated.types.Post
import com.github.mburyshynets.dgs.service.PostService
import com.netflix.graphql.dgs.DgsDataLoader
import org.dataloader.MappedBatchLoader
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executor

@DgsDataLoader(name = "topicPosts", maxBatchSize = 250)
class TopicPostsBatchLoader(
    private val asyncExecutor: Executor,
    private val postService: PostService,
) : MappedBatchLoader<Long, List<Post>> {

    override fun load(topicIds: Set<Long>): CompletableFuture<Map<Long, List<Post>>> {
        return CompletableFuture.supplyAsync({ postService.getPostsByTopicIds(topicIds) }, asyncExecutor)
    }
}
