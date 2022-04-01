package com.jetbrains.handson.httpapi

import kotlinx.serialization.Serializable

@Serializable
data class User(val login:String, var password: String, var mail: String?,var path_to_image: String?)

@Serializable
data class Login_MailOfUser(val login: String, val mail: String)
val users = mutableListOf<User>()


