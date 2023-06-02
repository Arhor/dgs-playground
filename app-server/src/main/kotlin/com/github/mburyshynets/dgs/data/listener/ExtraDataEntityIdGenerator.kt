package com.github.mburyshynets.dgs.data.listener

import com.github.mburyshynets.dgs.data.model.ExtraDataEntity
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ExtraDataEntityIdGenerator : BeforeConvertCallback<ExtraDataEntity> {

    override fun onBeforeConvert(aggregate: ExtraDataEntity): ExtraDataEntity {
        return when (aggregate.id) {
            null -> aggregate.copy(id = UUID.randomUUID())
            else -> aggregate
        }
    }
}
