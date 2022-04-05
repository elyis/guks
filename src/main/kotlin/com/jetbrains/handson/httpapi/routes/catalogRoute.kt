package com.jetbrains.handson.httpapi.routes

import com.jetbrains.handson.httpapi.data.ItemFromCatalog
import com.jetbrains.handson.httpapi.data.catalog
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import java.io.File

fun Route.catalogRoute()
{
    route("catalog")
    {

        post("addCategory/{name_ct}")
        {
            val new_category = call.parameters["name_ct"] ?: return@post call.respond(HttpStatusCode.BadRequest)
            val new_item = call.receive<ItemFromCatalog>()
            catalog.put(new_category, mutableListOf(new_item))
            call.respond(HttpStatusCode.Created)
        }


        //Добавление нового объекта в категорию
        post("add/{category}")
        {
            val category = call.parameters["category"] ?: return@post call.respond(HttpStatusCode.BadRequest)
            val new_item = call.receive<ItemFromCatalog>()
            var isRepeat = false

            catalog[category]?.forEach()
            {
                if (it.item == new_item.item)
                    isRepeat = true
            }
            if (!isRepeat) {
                catalog[category]?.add(new_item)
                call.respond(HttpStatusCode.Created)
            }
            else {
                call.respond(HttpStatusCode.NotModified)
            }
        }

        get("getProductIcon/{category}/{icon_name}")
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