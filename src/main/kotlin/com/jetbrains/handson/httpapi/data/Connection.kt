package com.jetbrains.handson.httpapi.data

import io.ktor.websocket.*
import java.util.*
import kotlin.collections.LinkedHashSet

data class Connection(val session: DefaultWebSocketSession, val login: String)

val connections = Collections.synchronizedSet<Connection?>(LinkedHashSet())