package com.jetbrains.handson.httpapi

import com.auth0.jwt.JWT
import com.jetbrains.handson.httpapi.data.User
import com.jetbrains.handson.httpapi.databases.UserDb
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

fun Route.registrationRoute()
{
    post("signup")
    {
        val user = call.receive<User>()
        var isUserRegistered = false

        transaction {
            UserDb.select { UserDb.login eq user.login }.forEach {
                isUserRegistered = true
            }
        }

        if (!isUserRegistered)
        {
            transaction {
                UserDb.insert {
                    it[login] = user.login
                    it[password] = user.password
                }
            }

            call.respond(HttpStatusCode.Created)
        }else
            call.respond(HttpStatusCode(409,"UserAlreadyRegistered"))

    }

}