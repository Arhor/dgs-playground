package com.github.mburyshynets.dgs.features.user.entity.converter

import com.github.mburyshynets.dgs.features.user.entity.Settings
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.WritingConverter

@WritingConverter
object SettingsWritingConverter : Converter<Settings, Long> {

    override fun convert(source: Settings): Long {
        var result = 0L
        for (setting in source.items) {
            result = result or (1L shl setting.index)
        }
        return result
    }
}
