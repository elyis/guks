package com.jetbrains.handson.httpapi.databases

import org.jetbrains.exposed.dao.id.IntIdTable

object User : IntIdTable()
{
    val userId = integer("userId").uniqueIndex().autoIncrement()
    val login = varchar("Имя пользователя",75)
    val password = varchar("пароль",75)
}
