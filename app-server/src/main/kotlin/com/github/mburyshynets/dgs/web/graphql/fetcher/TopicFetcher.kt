package com.github.mburyshynets.dgs.web.graphql.fetcher

import com.github.mburyshynets.dgs.graphql.generated.types.CreateTopicRequest
import com.github.mburyshynets.dgs.graphql.generated.types.Topic
import com.github.mburyshynets.dgs.service.TopicService
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails

@DgsComponent
class TopicFetcher(
    private val topicService: TopicService,
) {

    // TODO: Add pagination support
    @DgsQuery
    fun topics(): List<Topic> {
        return topicService.getAllTopics()
    }

    @DgsMutation
    @PreAuthorize("isAuthenticated()")
    fun createTopic(
        @InputArgument request: CreateTopicRequest,
        @AuthenticationPrincipal currentUser: UserDetails,
    ): Topic {
        return topicService.createNewTopic(request, currentUser)
    }
}
