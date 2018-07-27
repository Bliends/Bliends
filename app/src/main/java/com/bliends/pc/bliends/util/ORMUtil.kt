package com.bliends.pc.bliends.util

import android.content.Context
import ninja.sakib.pultusorm.core.PultusORM

class ORMUtil(context: Context) {
    val appPath: String = context.filesDir.absolutePath
    var tokenORM = PultusORM("token.db", appPath)
    var userORM = PultusORM("user.db" , appPath)
}