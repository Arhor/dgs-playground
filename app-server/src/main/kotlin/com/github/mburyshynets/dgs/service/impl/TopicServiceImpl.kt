package com.github.mburyshynets.dgs.service.impl

import com.github.mburyshynets.dgs.data.repository.TopicRepository
import com.github.mburyshynets.dgs.graphql.generated.types.CreateTopicRequest
import com.github.mburyshynets.dgs.graphql.generated.types.Topic
import com.github.mburyshynets.dgs.service.TopicService
import com.github.mburyshynets.dgs.service.mapper.TopicMapper
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TopicServiceImpl(
    private val topicRepository: TopicRepository,
    private val topicMapper: TopicMapper,
) : TopicService {

    @Transactional(readOnly = true)
    override fun getAllTopics(): List<Topic> {
        return topicRepository.findAll().map(topicMapper::mapToDTO)
    }

    @Transactional
    override fun createNewTopic(request: CreateTopicRequest, currentUser: UserDetails): Topic {
        // TODO: verify that current user executes request
        // if (currentUser.id != request.userId) {
        //     throw IllegalStateException("Cannot create topic for another user!")
        // }
        return topicMapper.mapToEntity(request)
            .let(topicRepository::save)
            .let(topicMapper::mapToDTO)
    }
}
