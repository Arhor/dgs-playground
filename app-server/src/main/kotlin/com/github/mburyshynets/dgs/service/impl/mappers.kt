package com.github.mburyshynets.dgs.service.impl

import com.github.mburyshynets.dgs.data.Settings
import com.github.mburyshynets.dgs.data.model.Post
import com.github.mburyshynets.dgs.data.model.PostExtension
import com.github.mburyshynets.dgs.data.model.User
import com.github.mburyshynets.dgs.graphql.generated.types.CreateUserRequest
import com.github.mburyshynets.dgs.graphql.generated.types.PostDto
import com.github.mburyshynets.dgs.graphql.generated.types.PostExtensionDto
import com.github.mburyshynets.dgs.graphql.generated.types.UserDto

fun CreateUserRequest.toEntity() = User(
    username = username,
    settings = settings?.let(::Settings),
)

fun User.toDto() = UserDto(
    id = this.id!!,
    username = this.username,
    settings = this.settings?.items,
)

fun Post.toDto() = PostDto(
    id = this.id!!,
    userId = this.userId,
    content = this.content,
)

fun PostExtension.toDto() = PostExtensionDto(
    id = this.id!!,
    postId = this.postId,
    additionalContent = this.additionalContent,
)
