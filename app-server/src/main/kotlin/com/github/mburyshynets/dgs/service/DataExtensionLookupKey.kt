package com.github.mburyshynets.dgs.service

import com.github.mburyshynets.dgs.graphql.generated.types.EntityType

data class DataExtensionLookupKey(val id: Long, val type: EntityType, val names: List<String>)
