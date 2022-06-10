package com.jetbrains.handson.httpapi

import com.auth0.jwt.JWT
import com.jetbrains.handson.httpapi.data.User
import com.jetbrains.handson.httpapi.data.users
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.registrationRoute()
{
    post("signup")
    {
        val user = call.receive<User>()

        if (!users.contains(user))
        {
            users.add(user)
            call.respond(HttpStatusCode.Created)
        }else
            call.respond(HttpStatusCode(409,"Account already exist"))
    }

}