package com.github.mburyshynets.dgs.features.user

import org.springframework.security.core.userdetails.UserDetails

open class ExtendedUserDetails private constructor(
    val id: Long?,
    private val userDetails: UserDetails,
) : UserDetails by userDetails {

    companion object {
        fun UserDetails.extendWith(id: Long?) = ExtendedUserDetails(
            id = id,
            userDetails = this,
        )
    }
}
