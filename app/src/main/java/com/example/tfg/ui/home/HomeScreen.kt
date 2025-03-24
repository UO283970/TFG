package com.example.tfg.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tfg.R
import com.example.tfg.ui.home.components.BooksCarousel
import com.example.tfg.ui.home.components.NoBooksMainScreen
import com.example.tfg.ui.home.components.TopNotifications
import com.example.tfg.ui.theme.TFGTheme

@Composable
fun HomeScreen(navigateTo: (route: String) -> Unit,viewModel: HomeViewModel = hiltViewModel()) {
    val state by viewModel.homeState.collectAsState()

    TFGTheme(dynamicColor = false) {
        Scaffold(contentWindowInsets = WindowInsets(0.dp),
            topBar = { TopNotifications(navigateTo) }) { innerPadding ->
            Column(Modifier.padding(innerPadding)) {
                HorizontalDivider(thickness = 1.dp)
                Column(Modifier.padding(start = 10.dp, end = 5.dp)) {
                    BasicButton("Todo")
                    Column {
                        BooksCarousel(
                            stringResource(id = R.string.home_recommended_books),
                            state.listOfBooks,
                            Modifier.weight(1f)
                        )
                        if(state.listOfReadingBooks.isEmpty()){
                            NoBooksMainScreen(
                                Modifier
                                    .weight(1f)
                                    .fillMaxWidth(), navigateTo)
                        }else{
                            BooksCarousel(
                                stringResource(id = R.string.home_reading_books),
                                state.listOfReadingBooks,
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
fun BasicButton(title: String) {
    OutlinedButton(
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.onBackground
        ),
        onClick = { /*TODO*/ },
    ) {
        Text(text = title)
    }
}