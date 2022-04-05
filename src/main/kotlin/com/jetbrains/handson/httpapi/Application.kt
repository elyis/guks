package com.jetbrains.handson.httpapi

import accounts
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
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

    registerRoutes()

    routing {
        get("allLogins")
        {
            call.respond(accounts)
        }
    }
}