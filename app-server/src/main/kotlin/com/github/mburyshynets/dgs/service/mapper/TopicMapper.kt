package com.github.mburyshynets.dgs.service.mapper

import com.github.mburyshynets.dgs.data.model.TopicEntity
import com.github.mburyshynets.dgs.graphql.generated.types.CreateTopicRequest
import com.github.mburyshynets.dgs.graphql.generated.types.Topic
import org.mapstruct.Mapper

@Mapper(config = MapstructCommonConfig::class)
interface TopicMapper {
    fun mapToDTO(it: TopicEntity): Topic
    fun mapToEntity(request: CreateTopicRequest): TopicEntity
}
