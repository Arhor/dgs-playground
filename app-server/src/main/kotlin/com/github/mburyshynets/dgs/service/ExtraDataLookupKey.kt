package com.github.mburyshynets.dgs.service

import com.github.mburyshynets.dgs.graphql.generated.types.ExtendedEntityType

data class ExtraDataLookupKey(val id: String, val type: ExtendedEntityType, val properties: List<String>? = null)
