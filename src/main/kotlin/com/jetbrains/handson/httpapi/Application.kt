package com.jetbrains.handson.httpapi

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.jetbrains.handson.httpapi.data.AuthUser
import com.jetbrains.handson.httpapi.data.User
import com.jetbrains.handson.httpapi.databases.UserDb
import com.jetbrains.handson.httpapi.databases.UserMessage
//import com.jetbrains.handson.httpapi.databases.UserMessage
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction


var args = listOf<String>().toTypedArray()

fun main(): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    install(ContentNegotiation)
    {
        gson()
        {

        }
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

    Database.connect("jdbc:mysql://localhost:3306/mess", driver = "com.mysql.cj.jdbc.Driver",
        user = "root", password = "root")

    transaction {
        addLogger(StdOutSqlLogger)
        SchemaUtils.create(UserDb,UserMessage)
    }

    routing {

        post("signin")
        {
            val user = call.receive<User>()

            var isFoundUser = false
            transaction {
                UserDb.select(UserDb.login eq user.login).forEach { UserIter ->
                    if (UserIter[UserDb.password] == user.password)
                        isFoundUser = true
                }
            }


            if (isFoundUser)
            {
                val token = JWT.create()
                        .withAudience(audience)
                        .withClaim("login",user.login)
                        .withIssuer(issuer)
                        .sign(Algorithm.HMAC256(secret))


                val authUser = AuthUser(user.login,user.password,token)
                call.respond(authUser)
            }
            else
                call.respondText("Incorrect login or password",status = HttpStatusCode.Unauthorized)
        }

        registrationRoute()

        authenticate {
            get("add")
            {
                transaction {
                    UserMessage.insert {
                        it[userId] = 2
                        it[message] = "hello teacher"
                    }
                    UserMessage.insert {
                        it[userId] = 3
                        it[message] = "hello guys"
                    }
                }
            }
        }

    }
}