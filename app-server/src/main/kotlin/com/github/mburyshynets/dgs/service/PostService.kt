package com.github.mburyshynets.dgs.service

import com.github.mburyshynets.dgs.graphql.generated.types.CreatePostRequest
import com.github.mburyshynets.dgs.graphql.generated.types.Post
import org.springframework.security.core.userdetails.UserDetails

interface PostService {
    fun createNewPost(request: CreatePostRequest, currentUser: UserDetails): Post
    fun getPostsByUserIds(userIds: Set<Long>): Map<Long, List<Post>>
    fun getPostsByTopicIds(topicIds: Set<Long>): Map<Long, List<Post>>
    fun getPostsUserId(userId: Long): List<Post>
    fun getPostsByTopicId(topicId: Long): List<Post>
}
