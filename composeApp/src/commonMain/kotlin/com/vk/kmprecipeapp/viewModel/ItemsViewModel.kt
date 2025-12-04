package com.vk.kmprecipeapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vk.kmprecipeapp.data.ItemRepository
import com.vk.kmprecipeapp.model.FoodDataResponse
import com.vk.kmprecipeapp.model.ImageResponse
import com.vk.kmprecipeapp.model.LoginRequest
import com.vk.kmprecipeapp.model.LoginResponse
import com.vk.kmprecipeapp.model.Recipe
import com.vk.kmprecipeapp.model.RecipesData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ItemsState(
    val items: Any? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

data class RecipesState(
    val recipesData: RecipesData = RecipesData(0,emptyList(),0,0),
    val isLoading: Boolean = false,
    val error: String? = null
)


data class LoginState(
    val loginResponse: LoginResponse? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
data class ImageState(
    val imageData: ImageResponse? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class ItemsViewModel(
    private val itemsRepo: ItemRepository,
): ViewModel() {

    private val _state = MutableStateFlow(ItemsState())
    val state: StateFlow<ItemsState> = _state

    private val _stateImage = MutableStateFlow(ImageState())
    val stateImage: StateFlow<ImageState> = _stateImage


    private val _stateRecipes = MutableStateFlow(RecipesState())
    val stateRecipes: StateFlow<RecipesState> = _stateRecipes


    val _stateLogin = MutableStateFlow(LoginState())
    val stateLogin: StateFlow<LoginState> = _stateLogin

    fun loadItems() {
        _state.update {
            it.copy(isLoading = false, error = null, items =  FoodDataResponse(emptyList()))
        }

        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val result = itemsRepo.getFoodData()
                println(result)
                _state.value = _state.value.copy(items = result, isLoading = false)
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, error = e.message)
                println(e.message)
            }
        }
    }

    fun getRecipesData() {
        _stateRecipes.update {
            it.copy(isLoading = false, error = null, recipesData = RecipesData(0,emptyList(),0,0))
        }

        viewModelScope.launch {
            _stateRecipes.value = _stateRecipes.value.copy(isLoading = true)
            try {
                val result = itemsRepo.getRecipesData()
                _stateRecipes.value = _stateRecipes.value.copy(recipesData = result, isLoading = false)
            } catch (e: Exception) {
                _stateRecipes.value = _stateRecipes.value.copy(isLoading = false, error = e.message)
                println(e.message)
            }
        }
    }

    @Suppress("SuspiciousIndentation")
    fun loginUser(loginRequest: LoginRequest){

        viewModelScope.launch {
            _stateLogin.value = _stateLogin.value.copy(isLoading = true)
            try {
                val result = itemsRepo.loginUser(loginRequest)
              _stateLogin.value = _stateLogin.value.copy(loginResponse = result, isLoading = false, error = result.message)
                println("result: $result")
            } catch (e: Exception) {
                _stateLogin.value = _stateLogin.value.copy(isLoading = false, error = e.message)
                println(e.message)
            }
        }
    }

    @Suppress("SuspiciousIndentation")
    fun uploadImage(image: ByteArray?, fileName: String){
        viewModelScope.launch {
            _stateImage.value = _stateImage.value.copy(isLoading = true)
            try {
                val result = itemsRepo.uploadImage(image!!, fileName)
                println(result)
                _stateImage.value = _stateImage.value.copy(imageData = result, isLoading = false)
                } catch (e: Exception) {
                _stateImage.value = _stateImage.value.copy(isLoading = false, error = e.message)

                    println(e.message)
            }
        }
    }

    private var selectedCategory: Recipe? = null

    fun selectCategory(category: Recipe){
        selectedCategory = category

    }
    fun getSelectedCategory(): Recipe? {
        return selectedCategory
    }


}

