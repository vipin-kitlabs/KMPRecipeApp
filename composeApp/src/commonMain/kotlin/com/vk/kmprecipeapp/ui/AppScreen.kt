package com.vk.kmprecipeapp.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vk.kmprecipeapp.viewModel.ItemsViewModel
import com.vk.kmprecipeapp.viewModel.PermissionViewModel
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.koin.mp.KoinPlatform.getKoin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScreen(viewModelPer: PermissionViewModel = getKoin().get()) {


    val viewModel: ItemsViewModel = koinInject()


    val navController = rememberNavController()
    val scope = rememberCoroutineScope()

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash"){
            SplashScreen(navController)
        }
        composable("login"){
            LoginScreen(navController,viewModel)
        }
        composable("home") {
            ItemsScreen(viewModel, navController)
        }

        composable("item/{itemId}") { backStackEntry ->
            ItemDetailsScreen(viewModel,navController)
        }
        composable("profile"){
            ProfileScreen(navController,viewModel)
        }


    }
    scope.launch {
        viewModelPer.askNotificationPermission()
    }


}
