package com.jetbrains.handson.httpapi.routes

import User
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import users

fun Route.loginRoute() {

    route("login")
    {
        post{
            val user = call.receive<User>()
//                val isAuthorized = false;
            val found_user = users.find { it.login == user.login && it.password == user.password }  ?: return@post call.respondText(
                "incorrect username or password",
                status = HttpStatusCode.NotFound
            )
            call.respond(found_user)

        }
    }

}