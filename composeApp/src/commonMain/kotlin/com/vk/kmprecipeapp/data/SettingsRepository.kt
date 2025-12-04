package com.vk.kmprecipeapp.data

import androidx.compose.ui.graphics.ImageBitmap
import com.russhwolf.settings.Settings
import com.vk.kmprecipeapp.model.LoginResponse
import com.vk.kmprecipeapp.utils.Cons.Companion.ACCESS_TOKEN
import io.ktor.util.encodeBase64
import kotlinx.serialization.json.Json
import network.chaintech.cmpimagepickncrop.utils.toByteArray
import okio.ByteString
import okio.ByteString.Companion.decodeBase64
import kotlin.io.encoding.Base64




class SettingsRepository(private val settings: Settings) {

    fun saveValue(key: String,userName: String) {
        settings.putString(key, userName)
    }

    fun getValue(key: String): String {
        return settings.getString(key, "")

    }

    fun saveAccessToken(accessToken: String) {
        settings.putString(ACCESS_TOKEN, accessToken)
    }

    fun getAccessToken(): String {
        return settings.getString(ACCESS_TOKEN, "")
    }

    @Suppress("SuspiciousIndentation")
    fun saveUserData(key: String, user: LoginResponse){
     val jsonString = Json.encodeToString(user)
        settings.putString(key, jsonString)
    }

    fun getUserData(key: String): LoginResponse? {
        val jsonString = settings.getString(key, "")
        return if (jsonString.isNotEmpty()) {
            Json.decodeFromString<LoginResponse>(jsonString)
        } else {
            null
        }

    }



    fun clear() {
        settings.clear()
    }


}


