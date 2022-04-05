package com.jetbrains.handson.httpapi

import User
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import users
import java.io.File

fun Route.profileSettings()
{
    route("profileSettings")
    {

        //Получение фото с профиля пользователя
        get("getIcon/{login}")
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

        changeProfile()

    }
}

fun Route.changeProfile()
{
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



// нахуй не надо но как пример чтобы потом заюзать
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