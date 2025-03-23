package com.example.tfg.ui.common

import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import androidx.palette.graphics.Palette

class ObtainColorsOfImage() {

    fun createPalette(bitmap: Bitmap): Palette {
        return Palette.from(bitmap).generate()
    }


    fun colorText(r: Float, g: Float, b: Float): Color {
        if ((r * 0.299 + g * 0.587 + b * 0.114) > 186) {
            return Color.Black
        }
        return Color.White
    }
}