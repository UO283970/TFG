package com.example.tfg.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.tfg.ui.common.navHost.MainAppNavigation
import com.example.tfg.ui.common.navHost.Routes
import com.example.tfg.ui.theme.TFGTheme

@Composable
fun NavigationBar(navController: NavHostController) {
    val items = listOf(
        Routes.Home, Routes.SearchScreen, Routes.FriendsScreen, Routes.ListsScreen, Routes.Profile
    )

    var selectedIndex by remember {
        mutableIntStateOf(0)
    }
    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }

    TFGTheme (dynamicColor = false){
        Scaffold(
            bottomBar = {
                AnimatedVisibility(
                    visible = bottomBarState.value,
                    enter = slideInVertically(initialOffsetY = { it }),
                    exit = slideOutVertically(targetOffsetY = { it }),
                ) {
                    NavigationBar(
                        containerColor = MaterialTheme.colorScheme.background,
                        contentColor = MaterialTheme.colorScheme.background,
                        tonalElevation = 0.dp
                    ) {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination

                        items.forEachIndexed { index, screen ->
                            NavigationBarItem(
                                icon = {
                                    Icon(
                                        painterResource(screen.icon), contentDescription = null,
                                        modifier = Modifier.size(28.dp)
                                    )
                                },
                                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                onClick = {
                                    selectedIndex = index
                                    navController.switchTabs(screen.route)
                                }
                            )
                        }
                    }
                }
            })
        { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                MainAppNavigation(navController, bottomBarState)
            }
        }
    }
}


fun NavHostController.switchTabs(route: String) {
    navigate(route) {

        launchSingleTop = true

        restoreState = true
    }
}