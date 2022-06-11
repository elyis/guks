package com.jetbrains.handson.httpapi.databases

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.Table.Dual.uniqueIndex

object UserDb : Table()
{
    val userId = integer("userId").autoIncrement()
    var login = varchar("Имя пользователя",75)
    val password = varchar("пароль",75)

    override val primaryKey = PrimaryKey(userId)
}
