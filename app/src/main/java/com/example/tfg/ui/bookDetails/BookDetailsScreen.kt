package com.example.tfg.ui.bookDetails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Star
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.Glide
import com.example.tfg.R
import com.example.tfg.ui.bookDetails.components.AddToListAndProgressBookInfo
import com.example.tfg.ui.bookDetails.components.MainBookInfoImage
import com.example.tfg.ui.common.BookAuthorText
import com.example.tfg.ui.common.BookTittleText
import com.example.tfg.ui.common.ChargingProgress
import com.example.tfg.ui.common.ObtainColorsOfImage
import com.example.tfg.ui.common.navHost.BookNavigationItems
import com.example.tfg.ui.theme.TFGTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate

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
                        palette.getDominantColor(
                            palette.lightVibrantSwatch?.rgb
                                ?: 0
                        )
                    )
                } else {
                    Color(
                        palette.getDominantColor(
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
                viewModel.finishLoad()
            }
        } else {
            viewModel.finishLoad()
        }
    }

    fun saveChanges(lisName: String, state: Boolean) {
        viewModel.saveChanges(lisName, state)
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
                    MainBookInfoImage(viewModel.bookState.bookForDetails.coverImage,color, textColor)
                    Column(modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp)) {
                        BookTittleText(viewModel.bookState.bookForDetails.tittle)
                        BookAuthorText(viewModel.bookState.bookForDetails.author)
                        BookSubjectButton(color, textColor, viewModel.bookState.bookForDetails.subjects)
                        Row(modifier = Modifier.padding(bottom = 10.dp), verticalAlignment = Alignment.CenterVertically) {
                            Spacer(Modifier.weight(1f))
                            Icon(Icons.Sharp.Star, null)
                            Icon(Icons.Sharp.Star, null)
                            Icon(Icons.Sharp.Star, null)
                            Icon(Icons.Sharp.Star, null)
                            Icon(Icons.Sharp.Star, null)
                            Spacer(Modifier.weight(1f))
                        }
                        AddToListAndProgressBookInfo(focus, viewModel, color, textColor)
                        Button({navigateTo(BookNavigationItems.ReviewScreen.route)}) { Text("Review")}
                        if (viewModel.bookInfo.dialogOpened) {
//                        AddBookToListsBottomSheet(
//                            color,
//                            textColor,
//                            viewModel.stringResourcesProvider,
//                            { viewModel.toggleDialog() },
//                            ::saveChanges,
//                            { navigateTo(it) }
//                        )
                        }
                        HorizontalDivider(Modifier.padding(top = 10.dp, bottom = 20.dp))
                        DateAndPagesRow(viewModel.bookState.bookForDetails.publicationDate, viewModel.bookState.bookForDetails.pages)
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
private fun DateAndPagesRow(date: LocalDate, pages: Int) {
    Row {
        if (date != LocalDate.MIN) {
            Text(
                stringResource(R.string.book_details_publish_year, date.year),
                modifier = Modifier.weight(1f)
            )
        }
        if (pages != 0) {
            Text(stringResource(R.string.book_details_pages, pages))
        }
    }
}

@Composable
fun DescriptionText(desc: String, maxLines: Int) {
    var expandedState by remember { mutableStateOf(false) }
    var showReadMoreButtonState by remember { mutableStateOf(false) }
    val maxLines = if (expandedState) 200 else maxLines

    Column {
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
                    color
                )
            ) {
                Text(
                    it,
                    color = textColor
                )
            }
        }
    }
}
