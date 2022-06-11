package com.jetbrains.handson.httpapi.data

import kotlinx.serialization.Serializable

@Serializable
data class AuthUser(
    val login: String,
    val password: String,
    val token: String
)

@Serializable
data class User(
    var login: String,
    val password: String
)