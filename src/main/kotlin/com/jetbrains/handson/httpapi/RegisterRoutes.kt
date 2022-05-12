package com.jetbrains.handson.httpapi

import com.jetbrains.handson.httpapi.routes.catalogRoute
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.routing.*

fun Application.registerRoutes() {

   routing {

      authenticate {

      }
      profileSettings()
      catalogRoute()
      registrationRoute()

   }

}