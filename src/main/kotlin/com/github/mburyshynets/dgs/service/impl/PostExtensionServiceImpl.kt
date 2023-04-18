package com.github.mburyshynets.dgs.service.impl

import com.github.mburyshynets.dgs.data.model.PostExtension
import com.github.mburyshynets.dgs.data.repository.PostExtensionRepository
import com.github.mburyshynets.dgs.graphql.generated.types.CreatePostExtensionRequest
import com.github.mburyshynets.dgs.graphql.generated.types.PostExtensionDto
import com.github.mburyshynets.dgs.service.PostExtensionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostExtensionServiceImpl @Autowired constructor(
    private val postExtensionRepository: PostExtensionRepository,
) : PostExtensionService {

    @Transactional
    override fun createNewPostExtension(request: CreatePostExtensionRequest): PostExtensionDto {
        return postExtensionRepository.save(
            PostExtension(
                postId = request.postId,
                additionalContent = request.additionalContent,
            )
        ).toDto()
    }

    @Transactional(readOnly = true)
    override fun getPostsByPostIds(keys: Set<Long>): Map<Long, List<PostExtensionDto>> {
        return postExtensionRepository.findAllByPostIdIn(keys)
            .map { it.toDto() }
            .groupBy { it.postId!! }
    }
}
