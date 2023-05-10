package com.github.mburyshynets.dgs.web.graphql.fetcher

import com.github.mburyshynets.dgs.data.Setting
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import java.util.EnumSet

@DgsComponent
class SettingsDataFetcher {

    @DgsQuery
    fun availableUserSettings(): EnumSet<Setting> {
        return EnumSet.allOf(Setting::class.java)
    }
}
