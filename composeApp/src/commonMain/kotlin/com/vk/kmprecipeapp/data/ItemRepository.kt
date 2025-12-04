package com.vk.kmprecipeapp.data

import com.vk.kmprecipeapp.model.FoodDataResponse
import com.vk.kmprecipeapp.model.ImageResponse
import com.vk.kmprecipeapp.model.LoginRequest
import com.vk.kmprecipeapp.model.LoginResponse
import com.vk.kmprecipeapp.model.RecipesData

interface ItemRepository {
    suspend fun getFoodData(): FoodDataResponse

    suspend fun getRecipesData(): RecipesData

    suspend fun loginUser(loginRequest: LoginRequest): LoginResponse

    suspend fun uploadImage(image: ByteArray?, fileName: String): ImageResponse
}