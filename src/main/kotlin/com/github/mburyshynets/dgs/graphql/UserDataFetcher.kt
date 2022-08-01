package com.github.mburyshynets.dgs.graphql

import com.github.mburyshynets.dgs.data.model.Post
import com.github.mburyshynets.dgs.data.model.User
import com.github.mburyshynets.dgs.data.repository.PostRepository
import com.github.mburyshynets.dgs.data.repository.UserRepository
import com.github.mburyshynets.dgs.graphql.generated.DgsConstants
import com.github.mburyshynets.dgs.graphql.generated.types.PostDto
import com.github.mburyshynets.dgs.graphql.generated.types.UserDto
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import java.util.concurrent.CompletableFuture

@DgsComponent
class UserDataFetcher(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
) {

    @DgsQuery
    fun user(@InputArgument username: String): UserDto? {
        return userRepository.findByUsername(username)?.toDto()
    }

    @DgsQuery
    fun users(): List<UserDto> {
        return userRepository.findAll().map(User::toDto).toList()
    }

    @DgsData(parentType = DgsConstants.USERDTO.TYPE_NAME)
    fun posts(dfe: DgsDataFetchingEnvironment): CompletableFuture<List<PostDto>> {
        val dataLoader = dfe.getDataLoader<Long, List<PostDto>>(PostDataLoader::class.java)
        val source = dfe.getSource<UserDto>()

        return dataLoader.load(source.id)
    }

    @DgsMutation
    fun createUser(@InputArgument username: String): UserDto {
        return userRepository.save(
            User(
                username = username
            )
        ).toDto()
    }

    @DgsMutation
    fun createUserPost(@InputArgument userId: Long, @InputArgument content: String): PostDto {
        return postRepository.save(
            Post(
                userId = userId,
                content = content
            )
        ).toDto()
    }
}
