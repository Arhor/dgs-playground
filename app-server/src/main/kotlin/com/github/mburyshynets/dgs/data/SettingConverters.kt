package com.github.mburyshynets.dgs.data

import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import java.util.EnumSet

@ReadingConverter
object SettingsReadingConverter : Converter<Long, Settings> {
    override fun convert(source: Long): Settings = Settings(
        items = EnumSet.noneOf(Setting::class.java).apply {
            for (setting in Setting.values()) {
                if ((source and (1L shl setting.index)) != 0L) {
                    add(setting)
                }
            }
        }
    )
}

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
