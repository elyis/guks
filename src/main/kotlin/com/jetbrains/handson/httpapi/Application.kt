package com.jetbrains.handson.httpapi

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(ContentNegotiation)
    {
        gson()
    }

//    val secret = environment.config.property("jwt.secret").getString()
//    val issuer = environment.config.property("jwt.issuer").getString()
//    val audience = environment.config.property("jwt.audience").getString()
//    val myRealm = environment.config.property("jwt.realm").getString()

//    install(Authentication) {
//        jwt {
//            realm = myRealm
//
//            verifier(
//                JWT
//                    .require(Algorithm.HMAC256(secret))
//                    .withAudience(audience)
//                    .withIssuer(issuer)
//                    .build()
//            )
//
//            validate { credential ->
//                if (credential.payload.getClaim("login").asString() != "") {
//                    JWTPrincipal(credential.payload)
//                } else {
//                    null
//                }
//            }
//        }
//    }


    routing {
        Authentication()
        route("authorization")
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
        get("allLogins")
        {
            call.respond(accounts)
        }
        Login()
        Catalog()
    }
}