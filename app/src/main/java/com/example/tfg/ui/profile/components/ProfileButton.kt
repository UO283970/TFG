package com.example.tfg.ui.profile.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.tfg.R
import com.example.tfg.model.user.User
import com.example.tfg.ui.common.navHost.ProfileNavigationItems

@Composable
fun EditButton(user: User?, navigateTo : (route: String) -> Unit) {

    Button(onClick = { navigateTo(ProfileNavigationItems.EditProfile.route) }) {
        Icon(Icons.Outlined.Edit, contentDescription = "")
        Text(stringResource(R.string.profile_edit_button))
    }
}