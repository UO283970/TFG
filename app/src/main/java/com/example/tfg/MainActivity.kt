package com.example.tfg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.tfg.ui.common.StringResourcesProvider
import com.example.tfg.ui.navigationBar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navigationBar(navController = rememberNavController(), StringResourcesProvider(applicationContext))
        }
    }
}
