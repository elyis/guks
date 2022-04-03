package com.jetbrains.handson.httpapi

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import java.io.File

fun Route.Authentication()
{
    route("registration")
    {
        post {
            val new_user = call.receive<User>()
            if (!users.contains(new_user)) {
                users.add(new_user)
            }else
                call.respondText("User with this login has already been registered")
        }
    }

    route("authorization")
    {
        post{
            val user = call.receive<User>()
            val found_user = users.find { it.login == user.login && it.password == user.password } ?: return@post call.respondText(
                                                                                                 "incorrect username or password",
                                                                                                      status = HttpStatusCode.NotFound
            )
            call.respond(found_user)
        }

        get("{login}")
        {
            val login = call.parameters["login"] ?: return@get call.respond(HttpStatusCode.BadRequest)
            val found_user = users.find { it.login == login} ?:  return@get call.respondText(
                                                            "incorrect username or password",
                                                                 status = HttpStatusCode.NotFound
            )
            val file = File("photos/profiles/${found_user.name_image}")

            if(file.exists())
            {
                //Скачивание
//                call.response.header(
//                    HttpHeaders.ContentDisposition,
//                    ContentDisposition.Attachment.withParameter(ContentDisposition.Parameters.FileName, icon)
//                        .toString()
//                )
                call.respondFile(file)
            }else
                call.respondText("There is no file with that name", status = HttpStatusCode.NotFound)
        }
    }
}

//fun Route.ChangeProfileSettings()
//{
//    route("change")
//    {
//        post("{login}/new_image/{image_name}")
//        {
//            val login = call.parameters["login"] ?: return@post call.respond(HttpStatusCode.BadRequest)
//        }
//
//        post("{login}/new_mail/{mail}")
//        {
//            val user = call.receive<UpdateMail>()
//            users.find { it.login == user.login }?.mail = user.mail
//        }
//    }
//}


fun Route.Catalog()
{
    route("catalog")
    {
        //Добавление нового объекта в категорию
        post("add/{category}")
        {
            val category = call.parameters["category"] ?: return@post call.respond(HttpStatusCode.BadRequest)
            val new_item = call.receive<ItemFromCatalog>()
            if (!catalog[category]?.contains(new_item)!!) {
                catalog[category]?.add(new_item)
            } else
                call.respondText("this category is in the catalog")
        }

        get("all")
        {
            call.respond(catalog)
        }

        get("{category}/{icon_name}")
        {
            val ct = call.parameters["category"]    ?:   return@get call.respond(HttpStatusCode.BadRequest)
            val icon = call.parameters["icon_name"] ?:   return@get call.respond(HttpStatusCode.BadRequest)
            val file = File("photos/iconsCatalog/$ct/$icon")

            if(file.exists())
            {
                //Скачивание
//                call.response.header(
//                    HttpHeaders.ContentDisposition,
//                    ContentDisposition.Attachment.withParameter(ContentDisposition.Parameters.FileName, icon)
//                        .toString()
//                )
                call.respondFile(file)
            }else
                call.respondText("There is no file with that name", status = HttpStatusCode.NotFound)
        }
    }
}