package com.example.tfg.ui.profile.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.tfg.R

@Composable
fun editButton() {
    Button(onClick = { /*TODO*/ }) {
        Icon(Icons.Outlined.Edit, contentDescription = "")
        Text(stringResource(R.string.profile_edit_button))
    }
}