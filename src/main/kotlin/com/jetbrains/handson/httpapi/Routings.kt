package com.jetbrains.handson.httpapi

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import java.io.File

fun Route.Login()
{
    route("login")
    {
        Profile()
    }
}

fun Route.Profile()
{
    //Получение фото с профиля пользователя
    get("profile/{login}")
    {
        val login = call.parameters["login"] ?: return@get call.respond(HttpStatusCode.BadRequest)
        val found_user = users.find { it.login == login} ?:  return@get call.respondText(
            "incorrect username or password",
            status = HttpStatusCode.NotFound
        )
        val file = File("photos/profiles/${found_user.name_image}")

        if(file.exists())
            call.respondFile(file)
        else
            call.respondText("There is no file with that name", status = HttpStatusCode.NotFound)
    }


    route("change")
    {
        post("mail/{new_main}")
        {
            val user = call.receive<User>()
            val mail = call.parameters["new_main"] ?: return@post call.respond(HttpStatusCode.BadRequest)

            users.forEach()
            {
                if (it.login == user.login)
                    it.mail = mail
            }
        }

        post("icon/new_icon")
        {
            val user = call.receive<User>()
            val icon = call.parameters["new_icon"] ?: return@post call.respond(HttpStatusCode.BadRequest)

            users.forEach()
            {
                if (it.login == user.login)
                    it.name_image = icon
            }
        }
    }
}

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
                    isRepeat = true
            }

            if (new_user.name_image == null)
                new_user.name_image = "unknown.svg"

            if (new_user.mail == null)
                new_user.mail = "not specified"

            if (!isRepeat) {
                users.add(new_user)
                accounts.add(OpenUserInformation(new_user.login,new_user.mail,new_user.name_image))
                call.respond(HttpStatusCode.Created)
            }else
                call.respond(HttpStatusCode.Unauthorized)
        }
    }
}

fun Route.Catalog()
{
    route("catalog")
    {

        post("addCategory/{name_ct}")
        {
//            val principal = call.principal<JWTPrincipal>()
//            val login = principal!!.payload.getClaim("login").asString()
//
//            if(login == "root")
//            {
                val new_category = call.parameters["name_ct"] ?: return@post call.respond(HttpStatusCode.BadRequest)
                val new_item = call.receive<ItemFromCatalog>()
                catalog.put(new_category, mutableListOf(new_item))
//            }
        }

        //Добавление нового объекта в категорию
        post("add/{category}")
        {
//            val principal = call.principal<JWTPrincipal>()
//            val login = principal!!.payload.getClaim("login").asString()
//
//            if(login == "root")
//            {
                val category = call.parameters["category"] ?: return@post call.respond(HttpStatusCode.BadRequest)
                val new_item = call.receive<ItemFromCatalog>()
                var isRepeat = false

                catalog[category]?.forEach()
                {
                    if (it.item == new_item.item)
                        isRepeat = true
                }
                if (!isRepeat)
                    catalog[category]?.add(new_item)
                else
                    call.respond(HttpStatusCode.NotModified)
//            }
        }

        get("{category}/{icon_name}")
        {
            val ct = call.parameters["category"]    ?:   return@get call.respond(HttpStatusCode.BadRequest)
            val icon = call.parameters["icon_name"] ?:   return@get call.respond(HttpStatusCode.BadRequest)
            val file = File("photos/iconsCatalog/$ct/$icon")

            if(file.exists())
                call.respondFile(file)
            else
                call.respondText("There is no file with that name", status = HttpStatusCode.NotFound)
        }

        get("all")
        {
            call.respond(catalog)
        }
    }
}

fun Route.UploadIcons()
{
    route("upload")
    {
        post("profile")
        {
            var fileName: String
            val multipartData = call.receiveMultipart()

            multipartData.forEachPart { part ->
                when (part) {
                    is PartData.FileItem -> {
                        fileName = part.originalFileName as String
                        val fileBytes = part.streamProvider().readBytes()
                        File("photos/profiles/$fileName").writeBytes(fileBytes)
                    }
                }
            }
        }

        post("icons/{category}")
        {
            var fileName: String
            val multipartData = call.receiveMultipart()

            multipartData.forEachPart { part ->
                when (part) {
                    is PartData.FileItem -> {
                        fileName = part.originalFileName as String
                        val fileBytes = part.streamProvider().readBytes()
                        File("photos/iconsCatalog/category/$fileName").writeBytes(fileBytes)
                    }
                }
            }
        }
    }
}