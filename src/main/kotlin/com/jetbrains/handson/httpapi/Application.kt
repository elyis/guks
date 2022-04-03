package com.jetbrains.handson.httpapi

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import java.io.File

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(ContentNegotiation)
    {
        gson()
    }
//    install(Authentication)
//    {
//        basic("auth-basic") {
//            realm = "Access to the '/' path"
//            validate { credentials ->
//                if (credentials.name == "Admin" && credentials.password == "toor") {
//                    UserIdPrincipal(credentials.name)
//                } else {
//                    null
//                }
//            }
//        }
//    }

    routing {
        Authentication()
        Catalog()
        get("test")
        {
            val file = File("photos/profiles/unknown.svg")
            call.response.header(
                HttpHeaders.ContentDisposition,
                ContentDisposition.Attachment.withParameter(ContentDisposition.Parameters.FileName, "index.png")
                    .toString()
            )
            call.respondFile(file)
        }
    }
}