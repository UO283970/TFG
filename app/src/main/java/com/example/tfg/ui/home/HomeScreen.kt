package com.example.tfg.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tfg.R
import com.example.tfg.model.Book
import com.example.tfg.ui.home.components.booksCarousel
import com.example.tfg.ui.home.components.noBooksMainScreen
import com.example.tfg.ui.home.components.topNotifications
import com.example.tfg.ui.theme.TFGTheme

@Composable
fun homeScreen(navController: NavHostController) {
    val items = listOf(
        Book("Words Of Radiance", "Brandon Sanderson", painterResource(R.drawable.prueba)),
        Book("Words Of Radiance", "Brandon Sanderson", painterResource(R.drawable.prueba)),
        Book("Words Of Radiance", "Brandon Sanderson", painterResource(R.drawable.prueba)),
        Book("Words Of Radiance", "Brandon Sanderson", painterResource(R.drawable.prueba))
    )

    TFGTheme(dynamicColor = false) {
        Scaffold(
            topBar = { topNotifications() }) { innerPadding ->
            Column(Modifier.padding(innerPadding)) {
                HorizontalDivider(thickness = 1.dp)
                Column(Modifier.padding(start = 10.dp, end = 5.dp)) {
                    basicButton("Todo")
                    Column {
                        booksCarousel(stringResource(id = R.string.home_recommended_books), items, Modifier.weight(1f))
                        noBooksMainScreen(Modifier.weight(1f).fillMaxWidth(),navController)
                    }
                }
            }
        }
    }
}

@Composable
fun basicButton(title: String, selectedOpt: Boolean = false) {
    OutlinedButton(
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.onBackground
        ),
        onClick = { /*TODO*/ },
    ) {
        Text(text = title)
    }
}