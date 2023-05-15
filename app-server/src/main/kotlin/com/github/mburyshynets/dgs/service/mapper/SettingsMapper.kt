package com.github.mburyshynets.dgs.service.mapper;

import com.github.mburyshynets.dgs.data.Setting
import com.github.mburyshynets.dgs.data.Settings
import org.mapstruct.Mapper
import java.util.EnumSet

@Mapper(config = MapstructCommonConfig::class)
abstract class SettingsMapper {

    fun wrap(value: EnumSet<Setting>?): Settings = Settings(value.safe())

    fun unwrap(value: Settings?): EnumSet<Setting> = value?.items.safe()

    private fun EnumSet<Setting>?.safe() = this ?: EnumSet.noneOf(Setting::class.java)
}
