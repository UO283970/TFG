package com.example.tfg.ui.search.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun filters() {
    Column (
        Modifier
            .padding(start = 10.dp)
            .fillMaxHeight()){
        filterButtonsSubjectRow()
        pagesFilter()
        orderByRow()
    }
}