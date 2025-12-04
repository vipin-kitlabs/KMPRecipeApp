package com.vk.kmprecipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.vk.kmprecipeapp.ui.AppScreen
import com.vk.kmprecipeapp.ui.ProfileScreen
import com.vk.kmprecipeapp.utils.NotificationPermissionHandler
import com.vk.kmprecipeapp.viewModel.PermissionViewModel
import org.koin.dsl.module
import org.koin.mp.KoinPlatform.getKoin

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val handler = NotificationPermissionHandler(this)

        getKoin().loadModules(
            listOf(
                module {
                    single { handler }
                    single { PermissionViewModel(get()) }
                }
            )
        )


        setContent {
            AppScreen()

        }
    }
}


@Preview
@Composable
fun AppAndroidPreview() {
    val navController = rememberNavController()
    ProfileScreen(navController = navController, viewModel = viewModel())
}