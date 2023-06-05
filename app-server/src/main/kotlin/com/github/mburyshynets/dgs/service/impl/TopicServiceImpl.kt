package com.github.mburyshynets.dgs.service.impl

import com.github.mburyshynets.dgs.data.repository.TopicRepository
import com.github.mburyshynets.dgs.graphql.generated.types.CreateTopicRequest
import com.github.mburyshynets.dgs.graphql.generated.types.Topic
import com.github.mburyshynets.dgs.service.DgsPermissionDeniedException
import com.github.mburyshynets.dgs.service.ExtendedUserDetails
import com.github.mburyshynets.dgs.service.Limit
import com.github.mburyshynets.dgs.service.Offset
import com.github.mburyshynets.dgs.service.OffsetBasedPageRequest
import com.github.mburyshynets.dgs.service.TopicService
import com.github.mburyshynets.dgs.service.mapper.TopicMapper
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
