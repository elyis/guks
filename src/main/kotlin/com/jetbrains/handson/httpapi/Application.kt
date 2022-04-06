package com.jetbrains.handson.httpapi

import RespondUser
import User
import accounts
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import users

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(ContentNegotiation)
    {
        gson()
    }

    val secret = environment.config.property("jwt.secret").getString()
    val issuer = environment.config.property("jwt.issuer").getString()
    val audience = environment.config.property("jwt.audience").getString()
    val myRealm = environment.config.property("jwt.realm").getString()

    install(Authentication) {
        jwt {
            realm = myRealm

            verifier(
                JWT
                    .require(Algorithm.HMAC256(secret))
                    .withAudience(audience)
                    .withIssuer(issuer)
                    .build()
            )

            validate { credential ->
                if (credential.payload.getClaim("login").asString() != "") {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }

    registerRoutes()

    routing {
        get("allLogins")
        {
            call.respond(accounts)
        }
        route("login")
        {
            post{
                var respondUser = RespondUser()
                val user = call.receive<User>()
                val found_user = users.find { it.login == user.login && it.password == user.password }  ?: return@post call.respondText(
                    "incorrect username or password",
                    status = HttpStatusCode.NotFound
                )

                val isCreate = users.find { it.login == user.login && it.password == user.password }
                if(isCreate != null) {
                    val token = JWT.create()
                        .withAudience(audience)
                        .withIssuer(issuer)
                        .withClaim("login", user.login)
//                .withExpiresAt(Date(System.currentTimeMillis() + 60000))
                        .sign(Algorithm.HMAC256(secret))

                    var respondUser = RespondUser(found_user.login,token,found_user.mail as String,found_user.name_image as String)
                    call.respond(respondUser)
                }else
                    call.respond(found_user)


            }
        }
    }
}