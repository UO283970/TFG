package com.example.tfg

import android.content.Context
import androidx.annotation.StringRes

class StringResourcesProvider(
    private val context: Context
) {
    fun getString(@StringRes stringResId: Int): String {
        return context.getString(stringResId)
    }
}