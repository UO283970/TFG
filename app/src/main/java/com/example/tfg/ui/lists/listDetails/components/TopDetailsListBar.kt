package com.example.tfg.ui.lists.listDetails.components

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.example.tfg.R
import com.example.tfg.ui.theme.myAppMainFont

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopDetailsListBar(returnToLastScreen: () -> Unit, tittle: String) {
    TopAppBar(
        title = {
            Text(
                text = tittle,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.SemiBold,
                fontFamily = myAppMainFont,
                fontSize = 26.sp
            )
        },
        navigationIcon = {
            IconButton(
                onClick = { returnToLastScreen()},
                modifier = Modifier.wrapContentSize()
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, stringResource(R.string.back_arrow))
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.background)
    )
}