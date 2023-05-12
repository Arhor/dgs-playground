package com.github.mburyshynets.dgs.service

import com.github.mburyshynets.dgs.graphql.generated.types.EntityType

data class ExtraDataLookupKey(val id: Long, val type: EntityType, val names: List<String>? = null)
