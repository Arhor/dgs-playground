package com.github.mburyshynets.dgs.web.graphql.fetcher

import com.github.mburyshynets.dgs.graphql.generated.DgsConstants
import com.github.mburyshynets.dgs.graphql.generated.types.CreatePostExtensionRequest
import com.github.mburyshynets.dgs.graphql.generated.types.PostDto
import com.github.mburyshynets.dgs.graphql.generated.types.PostExtensionDto
import com.github.mburyshynets.dgs.web.graphql.loader.PostExtensionDataLoader
import com.github.mburyshynets.dgs.service.PostExtensionService
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.InputArgument
import java.util.concurrent.CompletableFuture

@DgsComponent
class PostExtensionDataFetcher(private val postExtensionService: PostExtensionService) {

    @DgsData(parentType = DgsConstants.POSTDTO.TYPE_NAME)
    fun postExtensions(dfe: DgsDataFetchingEnvironment): CompletableFuture<List<PostExtensionDto>> {
        val dataLoader = dfe.getDataLoader<Long, List<PostExtensionDto>>(PostExtensionDataLoader::class.java)
        val source = dfe.getSource<PostDto>()

        return dataLoader.load(source.id)
    }

    @DgsMutation
    fun createPostExtension(@InputArgument request: CreatePostExtensionRequest): PostExtensionDto {
        return postExtensionService.createNewPostExtension(request)
    }
}
