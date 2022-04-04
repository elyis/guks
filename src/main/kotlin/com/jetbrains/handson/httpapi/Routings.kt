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
            var isRepeat = false
            users.forEach()
            {
                if (it.login == new_user.login)
                {
                    isRepeat = true
                    call.respondText("User with this login has already been registered", status = HttpStatusCode.Unauthorized)
                }
            }

            if (new_user.name_image == null)
                new_user.name_image = "unknown.svg"

            if (!isRepeat)
                users.add(new_user)
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

        //Получение фото с профиля пользователя
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
        post("addCategory/{name_ct}")
        {
            val new_category = call.parameters["name_ct"] ?: return@post call.respond(HttpStatusCode.BadRequest)
            val new_item = call.receive<ItemFromCatalog>()
            catalog.put(new_category, mutableListOf(new_item))
        }

        //Добавление нового объекта в категорию
        post("add/{category}")
        {
            val category = call.parameters["category"] ?: return@post call.respond(HttpStatusCode.BadRequest)
            val new_item = call.receive<ItemFromCatalog>()
            var isRepeat = false

            catalog[category]?.forEach()
            {
                if (it.item == new_item.item) {
                    isRepeat = true
                    call.respondText("this item is in the category", status = HttpStatusCode.NotModified)
                }
            }
            if (!isRepeat)
                catalog[category]?.add(new_item)
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

        get("all")
        {
            call.respond(catalog)
        }

    }
}