package com.github.mburyshynets.dgs.service.impl

import com.github.mburyshynets.dgs.data.repository.PostRepository
import com.github.mburyshynets.dgs.graphql.generated.types.CreatePostRequest
import com.github.mburyshynets.dgs.graphql.generated.types.Post
import com.github.mburyshynets.dgs.service.PostService
import com.github.mburyshynets.dgs.service.mapper.PostMapper
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostServiceImpl(
    private val postRepository: PostRepository,
    private val postMapper: PostMapper,
) : PostService {

    @Transactional
    override fun createNewPost(request: CreatePostRequest, currentUser: UserDetails): Post {
        // TODO: verify that current user executes request
        // if (currentUser.id != request.userId) {
        //     throw IllegalStateException("Cannot create topic for another user!")
        // }
        return postMapper.mapToEntity(request)
            .let { postRepository.save(it) }
            .let { postMapper.mapToDTO(it) }
    }

    @Transactional(readOnly = true)
    override fun getPostsByUserIds(userIds: Set<Long>): Map<Long, List<Post>> {
        return if (userIds.isEmpty()) {
            emptyMap()
        } else {
            postRepository
                .findAllByUserIdIn(userIds)
                .map { postMapper.mapToDTO(it) }
                .groupBy { it.userId!! }
        }
    }

    override fun getPostsByTopicIds(topicIds: Set<Long>): Map<Long, List<Post>> {
        return if (topicIds.isEmpty()) {
            emptyMap()
        } else {
            postRepository
                .findAllByTopicIdIn(topicIds)
                .map { postMapper.mapToDTO(it) }
                .groupBy { it.topicId }
        }
    }

    override fun getPostsUserId(userId: Long): List<Post> {
        return postRepository.findAllByUserId(userId).map { postMapper.mapToDTO(it) }
    }

    override fun getPostsByTopicId(topicId: Long): List<Post> {
        return postRepository.findAllByTopicId(topicId).map { postMapper.mapToDTO(it) }
    }
}
