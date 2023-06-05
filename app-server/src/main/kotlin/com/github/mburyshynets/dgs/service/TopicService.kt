package com.github.mburyshynets.dgs.service

import com.github.mburyshynets.dgs.graphql.generated.types.CreateTopicRequest
import com.github.mburyshynets.dgs.graphql.generated.types.Topic
import com.github.mburyshynets.dgs.service.impl.Limit
import com.github.mburyshynets.dgs.service.impl.Offset

interface TopicService {
    fun createNewTopic(request: CreateTopicRequest, currentUser: ExtendedUserDetails): Topic
    fun getAllTopics(offset: Offset, limit: Limit): List<Topic>
}
