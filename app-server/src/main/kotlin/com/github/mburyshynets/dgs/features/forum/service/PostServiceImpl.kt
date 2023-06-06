package com.github.mburyshynets.dgs.features.forum.service

import com.github.mburyshynets.dgs.features.forum.repository.PostRepository
import com.github.mburyshynets.dgs.generated.graphql.types.CreatePostRequest
import com.github.mburyshynets.dgs.generated.graphql.types.Post
import com.github.mburyshynets.dgs.common.DgsPermissionDeniedException
import com.github.mburyshynets.dgs.features.user.ExtendedUserDetails
import com.github.mburyshynets.dgs.features.forum.PostService
import com.github.mburyshynets.dgs.features.forum.mapper.PostMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostServiceImpl(
    private val postRepository: PostRepository,
    private val postMapper: PostMapper,
) : PostService {

    @Transactional
    override fun createNewPost(request: CreatePostRequest, currentUser: ExtendedUserDetails): Post {
        if (currentUser.id!!.toString() != request.userId) {
            throw DgsPermissionDeniedException("Cannot create topic for another user!")
        }
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
