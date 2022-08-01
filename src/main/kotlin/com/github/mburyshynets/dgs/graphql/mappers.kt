package com.github.mburyshynets.dgs.graphql

import com.github.mburyshynets.dgs.data.model.Post
import com.github.mburyshynets.dgs.data.model.User
import com.github.mburyshynets.dgs.graphql.generated.types.PostDto
import com.github.mburyshynets.dgs.graphql.generated.types.UserDto

fun User.toDto() = UserDto(
    id = id,
    username = username
)

fun Post.toDto() = PostDto(
    id = id,
    userId = userId,
    content = content,
)
