package com.example.tfg.ui.common

import android.content.Context
import androidx.annotation.ArrayRes
import androidx.annotation.StringRes

class StringResourcesProvider(
    private val context: Context
) {
    fun getString(@StringRes stringResId: Int): String {
        return context.getString(stringResId)
    }

    fun getStringWithParameters(@StringRes stringResId: Int, vararg args: Any): String {
        return context.getString(stringResId,*args)
    }

    fun getStringArray(@ArrayRes stringArrayId: Int): List<String> {
        return context.resources.getStringArray(stringArrayId).asList()
    }
}