package com.example.tfg

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.tfg.ui.NavigationBar
import com.example.tfg.ui.common.StringResourcesProvider
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var stringResourcesProvider: StringResourcesProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NavigationBar(navController = rememberNavController())
        }
    }

}
@HiltAndroidApp
class CoreApplication: Application()
