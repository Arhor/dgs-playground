package com.github.mburyshynets.dgs.service.mapper

import com.github.mburyshynets.dgs.data.model.UserEntity
import com.github.mburyshynets.dgs.graphql.generated.types.CreateUserRequest
import com.github.mburyshynets.dgs.graphql.generated.types.User
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(
    config = MapstructCommonConfig::class,
    uses = [
        PostMapper::class,
        OptionalMapper::class,
        SettingsMapper::class,
    ]
)
interface UserMapper {

    @Mapping(target = "id", ignore = true)
    fun mapToEntity(request: CreateUserRequest): UserEntity

    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "extensions", ignore = true)
    fun mapToDTO(user: UserEntity): User
}
