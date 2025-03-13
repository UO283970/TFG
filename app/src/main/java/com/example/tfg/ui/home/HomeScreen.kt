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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tfg.R
import com.example.tfg.ui.home.components.booksCarousel
import com.example.tfg.ui.home.components.noBooksMainScreen
import com.example.tfg.ui.home.components.topNotifications
import com.example.tfg.ui.theme.TFGTheme

@Composable
fun homeScreen(viewModel: HomeViewModel) {
    TFGTheme(dynamicColor = false) {
        Scaffold(
            topBar = { topNotifications(viewModel) }) { innerPadding ->
            Column(Modifier.padding(innerPadding)) {
                HorizontalDivider(thickness = 1.dp)
                Column(Modifier.padding(start = 10.dp, end = 5.dp)) {
                    basicButton("Todo")
                    Column {
                        booksCarousel(
                            stringResource(id = R.string.home_recommended_books),
                            viewModel.homeState.listOfBooks,
                            Modifier.weight(1f)
                        )
                        if(viewModel.homeState.listOfReadingBooks.isEmpty()){
                            noBooksMainScreen(
                                Modifier
                                    .weight(1f)
                                    .fillMaxWidth(), viewModel)
                        }else{
                            booksCarousel(
                                stringResource(id = R.string.home_reading_books),
                                viewModel.homeState.listOfReadingBooks,
                                Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun basicButton(title: String) {
    OutlinedButton(
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.onBackground
        ),
        onClick = { /*TODO*/ },
    ) {
        Text(text = title)
    }
}