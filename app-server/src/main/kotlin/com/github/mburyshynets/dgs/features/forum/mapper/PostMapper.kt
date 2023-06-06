package com.github.mburyshynets.dgs.features.forum.mapper

import com.github.mburyshynets.dgs.features.forum.entity.PostEntity
import com.github.mburyshynets.dgs.generated.graphql.types.CreatePostRequest
import com.github.mburyshynets.dgs.generated.graphql.types.Post
import com.github.mburyshynets.dgs.common.IgnoreAuditMappings
import com.github.mburyshynets.dgs.common.MapstructCommonConfig
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
