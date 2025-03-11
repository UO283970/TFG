package com.example.tfg.ui.common

import androidx.navigation.NavController

sealed class CommonEvents {
    object GoBackEvent: CommonEvents()

}
class CommonEventHandler(private val navController: NavController) {
    fun onEvent(event: CommonEvents){
        when(event){
            is CommonEvents.GoBackEvent -> {
                navController.popBackStack()
            }
        }
    }
}