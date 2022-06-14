package plugins

import com.jetbrains.handson.httpapi.data.Connection
import com.jetbrains.handson.httpapi.data.connections
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*


fun Route.configureRoute()
{
    webSocket("/chat")
    {
        send("You are connected!")
        for(frame in incoming)
        {
            val principal = call.principal<JWTPrincipal>()
            val login = principal!!.payload.getClaim("login").asString()
            val thisConnection = Connection(this,login)

            connections += thisConnection
            try {
                for (frame in incoming) {
                    frame as? Frame.Text ?: continue
                    val receivedText = frame.readText()
                    val textForAll = "$login: $receivedText"
                    connections.forEach {
                        it.session.send(textForAll)
                    }
                }
            }
            catch (e: Exception) {
                println(e.message)
            }

            finally {
                connections -= thisConnection
            }

        }
    }
}