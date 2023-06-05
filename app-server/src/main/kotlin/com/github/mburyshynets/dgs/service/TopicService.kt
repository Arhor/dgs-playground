package com.github.mburyshynets.dgs.service

import com.github.mburyshynets.dgs.generated.graphql.types.CreateTopicRequest
import com.github.mburyshynets.dgs.generated.graphql.types.Topic

interface TopicService {
    fun createNewTopic(request: CreateTopicRequest, currentUser: ExtendedUserDetails): Topic
    fun getAllTopics(offset: Offset, limit: Limit): List<Topic>
}
