package com.example.tfg.ui.common

import androidx.navigation.NavController

class CommonEventHandler(private val navController: NavController) {
    fun backEvent() {
        navController.popBackStack()
    }
}