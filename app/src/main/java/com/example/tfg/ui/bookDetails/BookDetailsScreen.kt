package com.example.tfg.ui.bookDetails

import android.graphics.BitmapFactory
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tfg.R
import com.example.tfg.ui.bookDetails.components.AddToListAndProgressBookInfo
import com.example.tfg.ui.bookDetails.components.MainBookInfoImage
import com.example.tfg.ui.common.ObtainColorsOfImage
import com.example.tfg.ui.common.bottonSheetLists.AddBookToListsBottomSheet
import com.example.tfg.ui.profile.components.statistics.reviews.BookAuthorText
import com.example.tfg.ui.profile.components.statistics.reviews.BookTittleText
import com.example.tfg.ui.theme.TFGTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailsScreen(navigateTo: (route: String) -> Unit, viewModel: BookDetailsViewModel = hiltViewModel()) {
    var focus = LocalFocusManager.current
    val palette =
        ObtainColorsOfImage().createPalette(
            BitmapFactory.decodeResource(
                LocalContext.current.resources,
                viewModel.bookInfo.book.coverImage
            )
        )
    val color = Color(
        palette.getDominantColor(
            palette.lightVibrantSwatch?.rgb
                ?: 0
        )
    )
    val textColor = ObtainColorsOfImage().colorText(
        r = color.alpha - color.red,
        g = color.alpha - color.green,
        b = color.alpha - color.blue
    )

    fun saveChanges(lisName: String, state: Boolean) {
        viewModel.saveChanges(lisName, state)
    }

    TFGTheme {
        Scaffold { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .imePadding()
            ) {
                MainBookInfoImage(viewModel)
                Column(modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp)) {
                    BookTittleText(viewModel.bookInfo.book.tittle)
                    BookAuthorText(viewModel.bookInfo.book.author)
                    BookSubjectButton(color)
                    AddToListAndProgressBookInfo(focus, viewModel)
                    if (viewModel.bookInfo.dialogOpened) {
                        AddBookToListsBottomSheet(
                            color,
                            textColor,
                            viewModel.stringResourcesProvider,
                            { viewModel.toggleDialog() },
                            ::saveChanges,
                            { navigateTo(it) }
                        )
                    }
                    Row {
                        Text(stringResource(R.string.book_user_rating))
                        Row(modifier = Modifier.clickable { viewModel.toggleRatingMenu() }) {
                            Text(viewModel.bookInfo.bookUserRating)
                            Text(stringResource(R.string.book_total_rating))
                            if (viewModel.bookInfo.ratingMenuOpen) {
                                Icon(
                                    Icons.Default.KeyboardArrowUp,
                                    stringResource(R.string.list_creation_dropdown_menu_down_arrow)
                                )
                            } else {
                                Icon(
                                    Icons.Default.KeyboardArrowDown,
                                    stringResource(R.string.list_creation_dropdown_menu_down_arrow)
                                )
                            }
                        }
                        DropdownMenu(
                            viewModel.bookInfo.ratingMenuOpen,
                            onDismissRequest = { viewModel.toggleRatingMenu() },
                            scrollState = rememberScrollState(),
                            modifier = Modifier.fillMaxHeight(0.25f)
                        ) {
                            for (numbers in 0..10) {
                                DropdownMenuItem(
                                    text = { Text(numbers.toString()) },
                                    onClick = {
                                    })
                            }
                        }

                    }
                }
            }
        }
    }
}

@Composable
private fun BookSubjectButton(color: Color) {
    Button(
        {},
        shape = (RoundedCornerShape(10.dp)),
        colors = ButtonDefaults.buttonColors(
            color
        )
    ) {
        Text(
            "Fantasía",
            color = (ObtainColorsOfImage().colorText(
                r = color.alpha - color.red,
                g = color.alpha - color.green,
                b = color.alpha - color.blue
            ))
        )
    }
}
