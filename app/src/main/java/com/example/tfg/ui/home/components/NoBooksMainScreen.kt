package com.example.tfg.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tfg.R
import com.example.tfg.ui.common.navHost.Routes
import com.example.tfg.ui.common.tittleBigText

@Composable
fun NoBooksMainScreen(modifier: Modifier, navigateTo: (String) -> Unit) {
    Column(modifier) {
        tittleBigText(stringResource(id = R.string.home_reading_books))
        Column(
            Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painterResource(R.drawable.prueba),
                contentDescription = stringResource(id = R.string.home_imageDescNoBooks),
                modifier = Modifier
                    .padding(top = 5.dp).weight(1f)
                    .clip(RoundedCornerShape(10.dp))
            )
            Text(
                stringResource(id = R.string.home_no_books),
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            OutlinedButton(onClick = {
                navigateTo(Routes.ListsScreen.route)
            }) {
                Icon(Icons.Outlined.Search, contentDescription = "", tint = MaterialTheme.colorScheme.onBackground)
                Text(
                    text = stringResource(id = R.string.home_search_books),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}
