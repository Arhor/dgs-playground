package com.github.mburyshynets.dgs.service.mapper

import com.github.mburyshynets.dgs.data.model.TopicEntity
import com.github.mburyshynets.dgs.graphql.generated.types.CreateTopicRequest
import com.github.mburyshynets.dgs.graphql.generated.types.Topic
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(config = MapstructCommonConfig::class)
interface TopicMapper {

    @Mapping(target = "id", ignore = true)
    fun mapToEntity(request: CreateTopicRequest): TopicEntity

    @Mapping(target = "posts", ignore = true)
    fun mapToDTO(it: TopicEntity): Topic
}
