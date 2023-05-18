package com.github.mburyshynets.dgs.service.impl

import com.github.mburyshynets.dgs.data.repository.PostRepository
import com.github.mburyshynets.dgs.graphql.generated.types.CreatePostRequest
import com.github.mburyshynets.dgs.graphql.generated.types.Post
import com.github.mburyshynets.dgs.service.PostService
import com.github.mburyshynets.dgs.service.mapper.PostMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostServiceImpl(
    private val postRepository: PostRepository,
    private val postMapper: PostMapper,
) : PostService {

    @Transactional
    override fun createNewPost(request: CreatePostRequest): Post {
        return postMapper.mapToEntity(request)
            .let { postRepository.save(it) }
            .let { postMapper.mapToDTO(it) }
    }

    @Transactional(readOnly = true)
    override fun getPostsByUserIds(keys: Set<Long>): Map<Long, List<Post>> {
        return if (keys.isEmpty()) {
            emptyMap()
        } else {
            postRepository
                .findAllByUserIdIn(keys)
                .map { postMapper.mapToDTO(it) }
                .groupBy { it.userId!! }
        }
    }
}
