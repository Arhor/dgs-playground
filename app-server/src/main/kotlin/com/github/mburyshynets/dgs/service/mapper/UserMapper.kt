package com.github.mburyshynets.dgs.service.mapper

import com.github.mburyshynets.dgs.data.model.UserEntity
import com.github.mburyshynets.dgs.generated.graphql.types.CreateUserRequest
import com.github.mburyshynets.dgs.generated.graphql.types.User
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

    @IgnoreAuditMappings
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", source = "password")
    fun mapToEntity(request: CreateUserRequest, password: String): UserEntity

    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "extraData", ignore = true)
    fun mapToDTO(user: UserEntity): User
}
