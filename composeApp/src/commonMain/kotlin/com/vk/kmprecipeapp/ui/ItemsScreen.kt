package com.vk.kmprecipeapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.vk.kmprecipeapp.model.Recipe
import com.vk.kmprecipeapp.utils.Cons
import com.vk.kmprecipeapp.utils.Cons.Companion.SELECTED_CATEGORY
import com.vk.kmprecipeapp.viewModel.ItemsViewModel
import com.vk.kmprecipeapp.viewModel.SettingsViewModel
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kmprecipeapp.composeapp.generated.resources.Res
import kmprecipeapp.composeapp.generated.resources.menu
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource


@OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ItemsScreen(
    viewModel: ItemsViewModel,
    navController: NavController
    ) {
        val state by viewModel.state.collectAsStateWithLifecycle()
        val stateRecipes by viewModel.stateRecipes.collectAsStateWithLifecycle()
        val settings = remember { SettingsViewModel() }


    var category = "Categorie"
    if (!settings.repo.getValue(SELECTED_CATEGORY).isEmpty()){
        category = "Recently viewed: ${settings.repo.getValue(SELECTED_CATEGORY)}"
    }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var showLogoutDialog by remember { mutableStateOf(false) }

    val userdata = settings.repo.getUserData(Cons.USER_DATA)

    val profilePic = settings.repo.getValue(Cons.PROFILE_PIC)

    println("User data  "+userdata)


        LaunchedEffect(Unit) {
//            viewModel.loadItems()
            viewModel.getRecipesData()
        }

    LogOutDialog(
        show = showLogoutDialog,
        onConfirm = {
            navController.navigate("login") {
                settings.repo.clear()
                popUpTo("home") { inclusive = true }

            }
        },
        onDismiss = {
            showLogoutDialog = false
        }
    )
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet() {

                Box(modifier = Modifier.fillMaxWidth()
                    .height(100.dp)
                    .background(
                        color = Color.LightGray
                    ),
                    ){

                    Row(
                        modifier = Modifier.align(Alignment.CenterStart),
                    ){
//                        Image(
//                            painter = painterResource(Res.drawable.account),
//                            contentDescription = null,
//                            modifier = Modifier
//                                .padding(10.dp)
//                                .size(100.dp)
//
//                        )
                        KamelImage(
                            resource = asyncPainterResource(profilePic),
                            contentDescription = "Profile",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .wrapContentWidth()
                                .padding(10.dp)
                                .size(80.dp)
                                .clip(CircleShape)

                        )
                        Text(text = userdata?.firstName!!.trim()+" "+userdata.lastName!!.trim(),
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(10.dp)
                                .align(Alignment.CenterVertically)

                        )
                    }
                }


                NavigationDrawerItem(
                    label = { Text("Home") },
                    selected = false,
                    modifier = Modifier.padding(start = 16.dp),
                    onClick = {
                        scope.launch { drawerState.close() }

                    }
                )

                NavigationDrawerItem(
                    label = { Text("Profile") },
                    selected = false,
                    modifier = Modifier.padding(start = 16.dp),
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate("profile")
                    }
                )

                NavigationDrawerItem(
                    label = { Text("Logout") },
                    selected = false,
                    modifier = Modifier.padding(start = 16.dp),
                    onClick = {
                        scope.launch { drawerState.close() }
                        showLogoutDialog = true
                    }
                )
            }
        }
    )

    {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {  },
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    scope.launch { drawerState.open() }
                                }
                            ) {
                                Image(
                                    painter = painterResource(Res.drawable.menu),
                                    contentDescription = "Menu",
                                    modifier = Modifier
                                        .padding(start = 16.dp)
                                )
                            }
                        },
                        actions = {
                            IconButton(
                                onClick = {
                                    navController.navigate("profile")

                                }
                            ) {
                                KamelImage(
                                    resource = asyncPainterResource(profilePic),
                                    contentDescription = "Profile",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .wrapContentWidth()

                                )
                            }


                        }
                    )
                },
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(start = 16.dp,end = 16.dp, bottom = 16.dp)
                ) {


                    when {
                        stateRecipes.isLoading -> {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }

                        stateRecipes.error != null -> {
                            Text(
                                text = "Error: ${stateRecipes.error}",
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.align(Alignment.Center)
                            )
                            println(stateRecipes.error)
                        }

                        else -> {
                            println(stateRecipes)
                            Column {
                                Text(
                                    modifier = Modifier.padding(bottom = 16.dp)
                                        .align(Alignment.Start),
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontSize = 20.sp
                                    ),
                                    text = category,
                                )

                                LazyVerticalGrid(
                                    columns = GridCells.Fixed(2),
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.spacedBy(12.dp),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    items(stateRecipes.recipesData.recipes.size) { item ->
                                        ItemRow(stateRecipes.recipesData.recipes.get(item), navController, viewModel)
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun ItemRow(item: Recipe, navController: NavController, viewModel: ItemsViewModel) {

        Column {
            Card(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .background(Color.White)
                    .clickable(
                        onClick = {
                            viewModel.selectCategory(item)
                            navController.navigate("item/${item.name}")
                        }
                    )
                ,
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            ) {


                Column(
                ) {

                    KamelImage(
                        resource = asyncPainterResource(item.image),
                        contentDescription = item.name,
                        modifier = Modifier
                            .wrapContentWidth()
                    )
                }
            }
            Text(
                text = item.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .wrapContentWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding( 16.dp)

            )
        }

    }









