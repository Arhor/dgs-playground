package com.github.mburyshynets.dgs.service.mapper

import com.github.mburyshynets.dgs.data.model.PostEntity
import com.github.mburyshynets.dgs.graphql.generated.types.CreatePostRequest
import com.github.mburyshynets.dgs.graphql.generated.types.Post
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(config = MapstructCommonConfig::class)
interface PostMapper {

    @IgnoreAuditMappings
    @Mapping(target = "id", ignore = true)
    fun mapToEntity(request: CreatePostRequest): PostEntity

    @Mapping(target = "extraData", ignore = true)
    fun mapToDTO(user: PostEntity): Post
}
