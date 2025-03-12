package com.example.tfg.ui.profile.components.editScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.tfg.R
import com.example.tfg.ui.profile.ProfileScreenEvent
import com.example.tfg.ui.profile.ProfileViewModel

@Composable
fun profileEditSwitch(profileViewModel: ProfileViewModel) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            stringResource(R.string.profile_edit_privacy),
            fontWeight = FontWeight.SemiBold,
            maxLines = 1
        )
        Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.End) {
            Switch(
                checked = profileViewModel.profileEditState.switchState,
                onCheckedChange = { profileViewModel.onEvent(ProfileScreenEvent.ChangeSwitch) })
        }
    }
}