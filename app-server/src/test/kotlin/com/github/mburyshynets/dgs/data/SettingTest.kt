package com.github.mburyshynets.dgs.data

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class SettingTest {

    @ParameterizedTest
    @EnumSource(Setting::class)
    fun `each Setting should have a unique index value`(
        // Given
        setting: Setting
    ) {
        // When
        val errorCodesByType = Setting.values().filter { it.index == setting.index }

        // Then
        assertThat(errorCodesByType)
            .describedAs("Settings with index set to [%s]", setting.index)
            .containsOnly(setting)
    }
}
