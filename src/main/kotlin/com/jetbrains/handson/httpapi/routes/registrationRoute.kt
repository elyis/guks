package com.jetbrains.handson.httpapi

import OpenUserInformation
import User
import accounts
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import users

fun Route.registrationRoute()
{
    route("registration")
    {
        post {
            val new_user = call.receive<User>()
            var isRepeat = false
            users.forEach()
            {
                if (it.login == new_user.login)
                    isRepeat = true
            }

            if (new_user.name_image == null)
                new_user.name_image = "unknown.svg"

            if (new_user.mail == null)
                new_user.mail = "not specified"

            if (!isRepeat) {
                users.add(new_user)
                accounts.add(OpenUserInformation(new_user.login,new_user.mail,new_user.name_image))
                call.respond(HttpStatusCode.Created)
            }else
                call.respond(HttpStatusCode(409, "AccountAlreadyExists"))
        }
    }
}