package com.example.tfg.ui.login.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun loginMainText(text:String){
    Text(
        text,
        fontSize = 28.sp,
        fontWeight = FontWeight.SemiBold
    )
}