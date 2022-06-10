package com.jetbrains.handson.httpapi.data

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val login: String,
    val password: String
)

data class AuthUser(
    val login: String,
    val password: String,
    val token: String
)






val users = mutableListOf<User>()