package com.github.mburyshynets.dgs.features.user.graphql.fetcher

import com.github.mburyshynets.dgs.features.user.entity.Setting
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import java.util.EnumSet

@DgsComponent
class SettingsFetcher {

    @DgsQuery
    fun availableUserSettings(): EnumSet<Setting> {
        return EnumSet.allOf(Setting::class.java)
    }
}
