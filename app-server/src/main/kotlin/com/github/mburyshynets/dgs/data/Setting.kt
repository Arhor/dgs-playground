package com.github.mburyshynets.dgs.data

const val MIN_SETTING_INDEX = 0
const val MAX_SETTING_INDEX = Long.SIZE_BITS - 1

private val INDEX_RANGE = MIN_SETTING_INDEX..MAX_SETTING_INDEX

enum class Setting(val index: Int) {
    SETTING_1(index = 0),
    SETTING_2(index = 1),
    SETTING_3(index = 2),
    ;

    init {
        require(index in INDEX_RANGE) {
            "'index' value must be in range $INDEX_RANGE, but $index is used for $name"
        }
    }
}
