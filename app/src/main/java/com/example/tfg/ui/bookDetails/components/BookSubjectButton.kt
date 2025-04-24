package com.example.tfg.ui.bookDetails.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BookSubjectButton(color: Color, textColor: Color, subjects: List<String>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.Companion.padding(top = 10.dp, bottom = 10.dp)
    ) {
        items(subjects) {
            Button(
                {},
                shape = (RoundedCornerShape(10.dp)),
                colors = ButtonDefaults.buttonColors(
                    disabledContainerColor = color
                ),
                enabled = false
            ) {
                Text(
                    it,
                    color = textColor
                )
            }
        }
    }
}