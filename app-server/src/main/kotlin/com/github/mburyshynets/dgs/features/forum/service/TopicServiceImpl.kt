package com.github.mburyshynets.dgs.features.forum.service

import com.github.mburyshynets.dgs.features.forum.repository.TopicRepository
import com.github.mburyshynets.dgs.generated.graphql.types.CreateTopicRequest
import com.github.mburyshynets.dgs.generated.graphql.types.Topic
import com.github.mburyshynets.dgs.common.DgsPermissionDeniedException
import com.github.mburyshynets.dgs.features.user.ExtendedUserDetails
import com.github.mburyshynets.dgs.common.Limit
import com.github.mburyshynets.dgs.common.Offset
import com.github.mburyshynets.dgs.common.OffsetBasedPageRequest
import com.github.mburyshynets.dgs.features.forum.TopicService
import com.github.mburyshynets.dgs.features.forum.mapper.TopicMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TopicServiceImpl(
    private val topicRepository: TopicRepository,
    private val topicMapper: TopicMapper,
) : TopicService {

    @Transactional
    override fun createNewTopic(request: CreateTopicRequest, currentUser: ExtendedUserDetails): Topic {
        if (currentUser.id!!.toString() != request.userId) {
            throw DgsPermissionDeniedException(message = "Cannot create topic for another user!")
        }
        return topicMapper.mapToEntity(request)
            .let(topicRepository::save)
            .let(topicMapper::mapToDTO)
    }

    @Transactional(readOnly = true)
    override fun getAllTopics(offset: Offset, limit: Limit): List<Topic> {
        return topicRepository
            .findAll(OffsetBasedPageRequest(offset, limit))
            .map(topicMapper::mapToDTO)
            .toList()
    }
}
