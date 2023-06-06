package com.github.mburyshynets.dgs.features.forum.mapper

import com.github.mburyshynets.dgs.features.forum.entity.TopicEntity
import com.github.mburyshynets.dgs.generated.graphql.types.CreateTopicRequest
import com.github.mburyshynets.dgs.generated.graphql.types.Topic
import com.github.mburyshynets.dgs.common.MapstructCommonConfig
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(config = MapstructCommonConfig::class)
interface TopicMapper {

    @Mapping(target = "id", ignore = true)
    fun mapToEntity(request: CreateTopicRequest): TopicEntity

    @Mapping(target = "posts", ignore = true)
    fun mapToDTO(it: TopicEntity): Topic
}
