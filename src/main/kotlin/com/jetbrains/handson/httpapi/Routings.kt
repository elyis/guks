package com.jetbrains.handson.httpapi

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.Authentication()
{
    route("registration")
    {
        post {
            val new_user = call.receive<User>()
            if (!users.contains(new_user)) {
                users.add(new_user)
            }
        }
    }

    route("authorization")
    {
        get("{login}")
        {
            val login = call.parameters["login"] ?: return@get call.respond(HttpStatusCode.BadRequest)
            val user = users.find { it.login == login } ?: return@get call.respondText(
                                                      "No customer with id $login",
                                                            status = HttpStatusCode.NotFound
                                                            )
            call.respond(user)
        }
    }
}

fun Route.ChangeProfileSettings()
{
    post("image")
    {

    }

    post("mail")
    {
       val new_mail = call.parameters["new_mail"] ?: return@post call.respond(HttpStatusCode.BadRequest)
        val user = call.receive<Login_MailOfUser>()
        users.find { it.login == user.login }?.mail = user.mail
    }
}

fun Route.Catalog()
{
    route("catalog")
    {
        post("add/{category}")
        {
            val category = call.parameters["category"] ?: return@post call.respond(HttpStatusCode.BadRequest)
            val new_item = call.receive<ItemFromCatalog>()
            if (!catalog[category]?.contains(new_item)!!) {
                catalog[category]?.add(new_item)
            } else
                call.respondText("this item is in the catalog")
        }

        get("all")
        {
            call.respond(catalog)
        }
    }

//    route("change")
//    {
//        post("{nameItem}")
//        {
//            val n_item = call.parameters["nameItem"] ?: return@post call.respond(HttpStatusCode.BadRequest)
//            val modified_item = call.receive<ItemFromCatalog>()
//            catalog.forEach()
//            {
//                if (it.item == n_item)
//                {
//                    it = modified_item
//                }
//            }
//        }
//    }
}