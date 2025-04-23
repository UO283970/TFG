package com.example.tfg.ui.bookDetails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.Glide
import com.example.tfg.R
import com.example.tfg.model.booklist.DefaultList
import com.example.tfg.ui.bookDetails.components.MainBookInfoImage
import com.example.tfg.ui.common.BookAuthorText
import com.example.tfg.ui.common.BookTittleText
import com.example.tfg.ui.common.ChargingProgress
import com.example.tfg.ui.common.ObtainColorsOfImage
import com.example.tfg.ui.common.UserPictureWithoutCache
import com.example.tfg.ui.common.bottonSheetLists.AddBookToListsBottomSheet
import com.example.tfg.ui.common.navHost.BookNavigationItems
import com.example.tfg.ui.theme.TFGTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.NumberFormat
import java.time.LocalDate
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailsScreen(navigateTo: (route: String) -> Unit, viewModel: BookDetailsViewModel = hiltViewModel()) {
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
                    MainBookInfoImage(viewModel.bookState.bookForDetails.coverImage, color, textColor)
                    Column(modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp)) {
                        BookTittleText(viewModel.bookState.bookForDetails.tittle)
                        BookAuthorText(viewModel.bookState.bookForDetails.author)
                        BookSubjectButton(color, textColor, viewModel.bookState.bookForDetails.subjects)
                        Row(
                            modifier = Modifier
                                .padding(bottom = 10.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable { viewModel.toggleRatingMenu() }) {
                                Text(stringResource(R.string.book_details_user_rating))
                                if (viewModel.bookState.bookForDetails.userScore == 0) {
                                    Icon(painterResource(R.drawable.empty_star), null, modifier = Modifier.size(24.dp))
                                } else {
                                    Row {
                                        Text(viewModel.bookState.bookForDetails.userScore.toString())
                                        Icon(painterResource(R.drawable.full_star), null, modifier = Modifier.size(24.dp))
                                    }
                                }
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(stringResource(R.string.book_details_user_mean_rating))
                                Row {
                                    Text(viewModel.bookState.bookForDetails.meanScore.toString())
                                    Icon(painterResource(R.drawable.full_star), null, modifier = Modifier.size(24.dp))
                                    Text(
                                        "(" + NumberFormat.getInstance(Locale.getDefault())
                                            .format(viewModel.bookState.bookForDetails.totalRatings) + ")"
                                    )
                                }
                            }
                            if (viewModel.bookState.bookForDetails.numberOfReviews != 0) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable {
                                    navigateTo(BookNavigationItems.ReviewScreen.route)
                                }) {
                                    Text(stringResource(R.string.book_details_number_reviews, viewModel.bookState.bookForDetails.numberOfReviews))
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy((-12.5).dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        var count = viewModel.bookState.bookForDetails.listOfUserProfilePicturesForReviews.size
                                        for (user in viewModel.bookState.bookForDetails.listOfUserProfilePicturesForReviews) {
                                            val signatureKey = remember { mutableStateOf(System.currentTimeMillis().toString()) }
                                            UserPictureWithoutCache(
                                                user, signatureKey, Modifier
                                                    .size(25.dp)
                                                    .clip(CircleShape)
                                                    .zIndex(count.toFloat())
                                            )
                                            count--;
                                        }
                                    }
                                }
                            } else {
                                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable {
                                    navigateTo(BookNavigationItems.ReviewCreationScreen.route)
                                }) {
                                    Text(stringResource(R.string.book_details_no_reviews))
                                    Icon(Icons.Default.Add, null, modifier = Modifier.size(24.dp))
                                }
                            }
                        }
                        if (viewModel.bookInfo.ratingMenuOpen) {
                            Dialog({ viewModel.toggleRatingMenu() }) {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight(0.2f)
                                ) {
                                    Column(Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                                        Row(
                                            horizontalArrangement = Arrangement.SpaceAround,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(start = 10.dp, end = 10.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            for (i in 1..10) {
                                                Icon(painterResource(R.drawable.empty_star), null, modifier = Modifier.size(24.dp))
                                            }

                                        }
                                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                                            TextButton({}) {
                                                Text("Borrar")
                                            }
                                            TextButton({}) {
                                                Text("Aceptar")
                                            }
                                        }
                                    }
                                }
                            }
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

@Composable
private fun DateAndPagesRow(date: LocalDate, pages: Int, progress: Int) {
    Row {
        if (date != LocalDate.MIN) {
            Text(
                stringResource(R.string.book_details_publish_year, date.year),
                modifier = Modifier.weight(1f),
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
        }
        if (pages != 0) {
            if (progress != -1) {
                Text(
                    stringResource(R.string.book_details_pages_with_progress, progress, pages),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    textAlign = TextAlign.Center
                )
            } else {
                Text(
                    stringResource(R.string.book_details_pages, pages),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun DescriptionText(desc: String, maxLines: Int) {
    var expandedState by remember { mutableStateOf(false) }
    var showReadMoreButtonState by remember { mutableStateOf(false) }
    val maxLines = if (expandedState) 200 else maxLines

    Column(modifier = Modifier.padding(bottom = 5.dp)) {
        Text(
            text = desc,
            fontWeight = FontWeight.SemiBold,
            overflow = TextOverflow.Ellipsis,
            maxLines = maxLines,
            onTextLayout = { textLayoutResult: TextLayoutResult ->
                if (textLayoutResult.lineCount > maxLines - 1) {
                    if (textLayoutResult.isLineEllipsized(maxLines - 1)) showReadMoreButtonState = true
                }
            }
        )
        if (showReadMoreButtonState) {
            Text(
                text = if (expandedState) stringResource(R.string.read_less) else stringResource(R.string.read_more),
                modifier = Modifier.clickable {
                    expandedState = !expandedState
                }
            )
        }
    }
}

@Composable
private fun BookSubjectButton(color: Color, textColor: Color, subjects: List<String>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
    ) {
        items(subjects) {
            Button(
                {},
                shape = (RoundedCornerShape(10.dp)),
                colors = ButtonDefaults.buttonColors(
                    disabledContainerColor = color
                ),
                enabled = false
            ) {
                Text(
                    it,
                    color = textColor
                )
            }
        }
    }
}
