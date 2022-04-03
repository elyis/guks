package com.jetbrains.handson.httpapi

import kotlinx.serialization.Serializable

@Serializable
data class User(val login:String, var password: String, var mail: String?,var name_image: String? = "unknown.svg")

@Serializable
data class UpdateMail(val login: String, val mail: String)


val users = mutableListOf<User>(
    User("Admin","toor","")
)


