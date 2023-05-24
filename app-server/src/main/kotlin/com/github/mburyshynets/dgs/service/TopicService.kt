package com.github.mburyshynets.dgs.service

import com.github.mburyshynets.dgs.graphql.generated.types.CreateTopicRequest
import com.github.mburyshynets.dgs.graphql.generated.types.Topic
import org.springframework.security.core.userdetails.UserDetails

interface TopicService {
    fun getAllTopics(): List<Topic>
    fun createNewTopic(request: CreateTopicRequest, currentUser: UserDetails): Topic
}
