package com.vk.kmprecipeapp.network

import com.vk.kmprecipeapp.model.FoodDataResponse
import com.vk.kmprecipeapp.model.ImageResponse
import com.vk.kmprecipeapp.model.LoginRequest
import com.vk.kmprecipeapp.model.LoginResponse
import com.vk.kmprecipeapp.model.RecipesData
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType

class ApiClient(
    private val client: HttpClient,
) {

    private val baseUrl = "https://dummyjson.com/"
     private val baseUrl1 = "https://www.themealdb.com/api/json/v1/1/categories.php"
    suspend fun getFoodData(): FoodDataResponse {
        return client.get(baseUrl1).body<FoodDataResponse>()
    }

    suspend fun getRecipesData(): RecipesData {
        return client.get(baseUrl+"recipes").body<RecipesData>()
    }
    suspend fun loginUser(loginRequest: LoginRequest): LoginResponse {
        return client.post(baseUrl+"auth/login"){
            contentType(ContentType.Application.Json)
            setBody(loginRequest)
        }.body<LoginResponse>()

    }

    suspend fun uploadImage(image: ByteArray?, fileName: String): ImageResponse {
        return client.submitFormWithBinaryData(
            url = "https://upload.imagekit.io/api/v1/files/upload",
            formData = formData {
                append( "file",
                    image!!,
                    Headers.build {
                        append(HttpHeaders.ContentDisposition, "filename=$fileName")
                        append(HttpHeaders.ContentType, "image/jpeg")
                    }
                )
                append("fileName", fileName)
            }
        ){
            header(HttpHeaders.Authorization, "Basic cHJpdmF0ZV9NL1d5OUVXbm5NbTRpbXhhNDZXWDhtb0M4L2c9Og==")
            header(HttpHeaders.Accept, "application/json")
        }.body<ImageResponse>()
    }
}