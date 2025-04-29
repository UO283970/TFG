package com.example.tfg.ui.bookDetails

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.Glide
import com.example.tfg.R
import com.example.tfg.model.booklist.DefaultList
import com.example.tfg.ui.bookDetails.components.BookSubjectButton
import com.example.tfg.ui.bookDetails.components.DateAndPagesRow
import com.example.tfg.ui.bookDetails.components.DescriptionText
import com.example.tfg.ui.bookDetails.components.MainBookInfoImage
import com.example.tfg.ui.bookDetails.components.RatingAndReviewRow
import com.example.tfg.ui.common.BookAuthorText
import com.example.tfg.ui.common.BookTittleText
import com.example.tfg.ui.common.ChargingProgress
import com.example.tfg.ui.common.ObtainColorsOfImage
import com.example.tfg.ui.common.RatingDialog
import com.example.tfg.ui.common.bottonSheetLists.AddBookToListsBottomSheet
import com.example.tfg.ui.theme.TFGTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailsScreen(navigateTo: (route: String) -> Unit, returnToLastScreen: () -> Unit,navigateToSearch: (author: String, searchFor: String) -> Unit, viewModel: BookDetailsViewModel = hiltViewModel()) {
    var focus = LocalFocusManager.current
    val context = LocalContext.current
    val isDarkMode = isSystemInDarkTheme()
    var color by remember { mutableStateOf(Color.White) }
    var textColor by remember { mutableStateOf(Color.Black) }

    LaunchedEffect(viewModel.bookState.bookForDetails.coverImage) {
        if (viewModel.bookState.bookForDetails.coverImage != "") {
            withContext(Dispatchers.IO) {
                val bitMap = Glide.with(context).asBitmap().load(viewModel.bookState.bookForDetails.coverImage).submit().get()

                val palette =
                    ObtainColorsOfImage().createPalette(
                        bitMap
                    )

                val insideColor = if (isDarkMode) {
                    Color(
                        palette.getDarkMutedColor(
                            palette.lightVibrantSwatch?.rgb
                                ?: 0
                        )
                    )
                } else {
                    Color(
                        palette.getLightMutedColor(
                            palette.darkVibrantSwatch?.rgb
                                ?: 0
                        )
                    )
                }

                color = insideColor

                textColor = ObtainColorsOfImage().colorText(
                    r = (insideColor.red) * 255,
                    g = (insideColor.green) * 255,
                    b = (insideColor.blue) * 255
                )
            }
        }
    }

    fun saveChanges(list: DefaultList, state: Boolean) {
        viewModel.saveChanges(list, state)
    }

    if (!viewModel.bookInfo.loadInfo) {
        TFGTheme {
            Scaffold { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState())
                        .imePadding()
                ) {
                    MainBookInfoImage(viewModel.bookState.bookForDetails.coverImage, color, textColor) { returnToLastScreen }
                    Column(modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp)) {
                        BookTittleText(viewModel.bookState.bookForDetails.tittle)
                        BookAuthorText(viewModel.bookState.bookForDetails.author, navigateToSearch)
                        BookSubjectButton(color, textColor, viewModel.bookState.bookForDetails.subjects)
                        RatingAndReviewRow(
                            viewModel,
                            navigateTo,
                            viewModel.bookState.bookForDetails.userScore,
                            viewModel.bookState.bookForDetails.meanScore,
                            viewModel.bookState.bookForDetails.totalRatings,
                            viewModel.bookState.bookForDetails.listOfUserProfilePicturesForReviews,
                            viewModel.bookState.bookForDetails.numberOfReviews
                        )
                        if (viewModel.bookInfo.ratingMenuOpen) {
                            RatingDialog(
                                color,
                                viewModel.bookState.bookForDetails.userScore,
                                { viewModel.toggleRatingMenu() },
                                { viewModel.saveRating() },
                                { viewModel.changeUserScore(it) },
                                { viewModel.changeDeleted(it) })
                        }
                        Button(
                            {
                                focus.clearFocus(true)
                                viewModel.openAddList()
                            },
                            modifier = Modifier
                                .padding(end = 10.dp)
                                .fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(color)
                        ) {
                            if (viewModel.bookInfo.inListButtonString == stringResource(R.string.book_add_to_list)) {
                                Icon(Icons.Default.Add, null, tint = textColor)
                            }
                            Text(viewModel.bookInfo.inListButtonString, color = textColor)
                        }
                        if (viewModel.bookInfo.addToListOpen) {
                            AddBookToListsBottomSheet(
                                color,
                                textColor,
                                viewModel.stringResourcesProvider,
                                viewModel.listRepository,
                                viewModel.bookState.bookForDetails,
                                viewModel.listsState,
                                { viewModel.openAddList() },
                                ::saveChanges,
                                { navigateTo(it) }
                            )
                        }
                        HorizontalDivider(Modifier.padding(top = 10.dp, bottom = 20.dp))
                        DateAndPagesRow(
                            viewModel.bookState.bookForDetails.publicationDate,
                            viewModel.bookState.bookForDetails.pages,
                            viewModel.bookState.bookForDetails.userProgression
                        )
                        if (viewModel.bookState.bookForDetails.details != "") {
                            HorizontalDivider(Modifier.padding(top = 20.dp, bottom = 10.dp))
                            DescriptionText(viewModel.bookState.bookForDetails.details, 6)
                        }
                    }
                }
            }
        }
    } else {
        ChargingProgress()
    }
}