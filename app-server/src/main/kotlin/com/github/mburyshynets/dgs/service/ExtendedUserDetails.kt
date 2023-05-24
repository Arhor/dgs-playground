package com.github.mburyshynets.dgs.service

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

open class ExtendedUserDetails(
    val id: Long?,
    username: String,
    password: String,
    authorities: Collection<GrantedAuthority>,
) : User(username, password, authorities)
