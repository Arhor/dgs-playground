package com.github.mburyshynets.dgs.service.mapper;

import org.mapstruct.MapperConfig
import org.mapstruct.MappingConstants
import org.mapstruct.NullValueMappingStrategy

@MapperConfig(
    componentModel = MappingConstants.ComponentModel.SPRING,
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
)
class MapstructCommonConfig
