package com.github.mburyshynets.dgs.features.forum

import com.github.mburyshynets.dgs.common.Limit
import com.github.mburyshynets.dgs.common.Offset
import com.github.mburyshynets.dgs.features.user.ExtendedUserDetails
import com.github.mburyshynets.dgs.generated.graphql.types.CreateTopicRequest
import com.github.mburyshynets.dgs.generated.graphql.types.Topic

interface TopicService {
    fun createNewTopic(request: CreateTopicRequest, currentUser: ExtendedUserDetails): Topic
    fun getAllTopics(offset: Offset, limit: Limit): List<Topic>
}
