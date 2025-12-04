package com.vk.kmprecipeapp.data

import com.vk.kmprecipeapp.model.FoodDataResponse
import com.vk.kmprecipeapp.model.ImageResponse
import com.vk.kmprecipeapp.model.LoginRequest
import com.vk.kmprecipeapp.model.LoginResponse
import com.vk.kmprecipeapp.model.RecipesData
import com.vk.kmprecipeapp.network.ApiClient

class ItemRepositoryImpl(private val apiClient: ApiClient): ItemRepository {
    override suspend fun getFoodData(): FoodDataResponse {
        return apiClient.getFoodData()
    }

    override suspend fun getRecipesData(): RecipesData {
        return apiClient.getRecipesData()
    }

    override suspend fun loginUser(loginRequest: LoginRequest): LoginResponse {
        return apiClient.loginUser(loginRequest)
    }

    override suspend fun uploadImage(
        image: ByteArray?,
        fileName: String
    ): ImageResponse {
        return apiClient.uploadImage(image!!, fileName)
    }

}