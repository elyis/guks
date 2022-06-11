package com.jetbrains.handson.httpapi.databases

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.CurrentDateTime
import org.jetbrains.exposed.sql.`java-time`.datetime

object UserMessage : Table()
{
    val userId = integer("userId").references(UserDb.userId)
    val message = varchar("Сообщение",500)
    val sendingTime = datetime("dateCreateMessage").defaultExpression(CurrentDateTime())
}