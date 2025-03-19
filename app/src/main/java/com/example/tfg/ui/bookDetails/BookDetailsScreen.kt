package com.example.tfg.ui.bookDetails

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.palette.graphics.Palette
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.tfg.R
import com.example.tfg.ui.common.navHost.ListNavigationItems
import com.example.tfg.ui.profile.components.statistics.reviews.BookAuthorText
import com.example.tfg.ui.profile.components.statistics.reviews.BookTittleText
import com.example.tfg.ui.theme.TFGTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailsScreen(navigateTo: (route: String) -> Unit, viewModel: BookDetailsViewModel = hiltViewModel()) {
    val addToListState by viewModel.menuListSate.collectAsState()
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.add_animation)
    )
    var focus = LocalFocusManager.current

    TFGTheme {
        Scaffold { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                val constraints = LocalConfiguration.current.screenHeightDp.dp
                Box(
                    modifier = Modifier.height(constraints * 0.4f)
                ) {
                    Image(
                        painterResource(viewModel.bookInfo.book.coverImage),
                        stringResource(R.string.book_image),
                        Modifier
                            .clip(RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp))
                            .blur(18.dp)
                            .fillMaxHeight(fraction = 0.9f),
                        contentScale = ContentScale.Crop
                    )
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(bottom = 5.dp)
                    ) {
                        Image(
                            painterResource(viewModel.bookInfo.book.coverImage),
                            stringResource(R.string.book_image),
                            Modifier
                                .fillMaxHeight(fraction = 0.9f)
                                .clip(RoundedCornerShape(16.dp))
                        )
                    }
                }
                Column(modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp)) {
                    BookTittleText(viewModel.bookInfo.book.tittle)
                    BookAuthorText(viewModel.bookInfo.book.author)
                    val palette =
                        createPalette(
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
                    val textColor = colorText(
                        r = color.alpha - color.red,
                        g = color.alpha - color.green,
                        b = color.alpha - color.blue
                    )
                    BookSubjectButton(color)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Button(
                            {
                                focus.clearFocus(true)
                                viewModel.changeListAddMenuOpen(!viewModel.menuListSate.value.listAddMenuOpen)
                            },
                            modifier = Modifier.padding(end = 10.dp)
                        ) {
                            Icon(Icons.Default.Add, "")
                            Text(stringResource(R.string.book_add_to_list))
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(stringResource(R.string.book_pages))
                            OutlinedTextField(
                                value = viewModel.bookInfo.totalPages.toString(),
                                onValueChange = { viewModel.changePagesRead(it) },
                                modifier = Modifier
                                    .weight(1f),
                                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End),
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedBorderColor = Color.Transparent,
                                    focusedBorderColor = Color.Transparent
                                ),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Done
                                ),
                                keyboardActions =
                                    KeyboardActions(
                                        onDone = {
                                            focus.clearFocus()
                                        }
                                    )
                            )
                            Text("/" + viewModel.bookInfo.book.pages.toString())
                        }
                    }
                    if (addToListState.listAddMenuOpen) {
                        Dialog(
                            { viewModel.changeListAddMenuOpen(false) },
                            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
                        ) {
                            var dialogFocus = LocalFocusManager.current
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(16.dp))
                                    .fillMaxWidth()
                                    .height(constraints * 0.8f)
                                    .background(color)
                                    .padding(10.dp)
                            ) {
                                Column {
                                    for (list in addToListState.checkboxDefaultList) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                list.key.listName, color = textColor, modifier = Modifier.weight(1f)
                                            )
                                            Checkbox(
                                                checked = list.value,
                                                onCheckedChange = {
                                                    dialogFocus.clearFocus()
                                                    viewModel.changeSelectedDefaultList(
                                                        list.key,
                                                        !list.value
                                                    )
                                                })
                                        }
                                    }
                                    HorizontalDivider()
                                    TextField(
                                        value = addToListState.userQuery,
                                        onValueChange = { viewModel.changeUserQuery(it) },
                                        colors = TextFieldDefaults.colors(
                                            focusedContainerColor = color,
                                            unfocusedContainerColor = color,
                                            focusedTextColor = textColor,
                                            unfocusedTextColor = textColor,
                                            cursorColor = textColor,
                                            focusedIndicatorColor = textColor
                                        ),
                                        singleLine = true,
                                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                                        keyboardActions = KeyboardActions(onSearch = {
                                            viewModel.filterListByUserQuery()
                                            dialogFocus.clearFocus()
                                        }),
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    LazyColumn {
                                        for (list in addToListState.checkboxUserList) {
                                            item {
                                                var animationState by remember { mutableStateOf(false) }
                                                val startAnimation = rememberLottieAnimatable()
                                                var midState by remember { mutableStateOf(list.value) }

                                                LaunchedEffect(midState) {
                                                    val minProgress = if (midState) 0f else 0.5f
                                                    val maxProgress = if (midState) 0.5f else 1f
                                                    if (!animationState) return@LaunchedEffect

                                                    startAnimation.animate(
                                                        composition = composition,
                                                        clipSpec = LottieClipSpec.Progress(minProgress, maxProgress),
                                                        speed = 1f
                                                    )
                                                }

                                                Row(verticalAlignment = Alignment.CenterVertically) {
                                                    Text(
                                                        list.key.listName,
                                                        color = textColor,
                                                        modifier = Modifier.weight(1f)
                                                    )
                                                    LottieAnimation(
                                                        composition,
                                                        progress = { startAnimation.progress },
                                                        modifier = Modifier
                                                            .size(38.dp)
                                                            .padding(top = 5.dp)
                                                            .clickable {
                                                                midState = !midState
                                                                animationState = true
                                                                viewModel.changeSelectedUserList(list.key, !list.value)
                                                            },
                                                    )
                                                }
                                            }
                                        }
                                        item {
                                            Row(
                                                modifier = Modifier
                                                    .clickable {
                                                        viewModel.changeListAddMenuOpen(!viewModel.menuListSate.value.listAddMenuOpen)
                                                        navigateTo(ListNavigationItems.ListCreation.route)
                                                    }
                                                    .padding(top = 10.dp)) {
                                                Icon(Icons.Default.Add, null, tint = textColor)
                                                Text(stringResource(R.string.book_add_new_list), color = textColor)
                                            }
                                        }
                                    }
                                }

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
            color = (colorText(
                r = color.alpha - color.red,
                g = color.alpha - color.green,
                b = color.alpha - color.blue
            ))
        )
    }
}

private fun createPalette(bitmap: Bitmap): Palette {
    return Palette.from(bitmap).generate()
}


fun colorText(r: Float, g: Float, b: Float): Color {
    if ((r * 0.299 + g * 0.587 + b * 0.114) > 186) {
        return Color.Black
    }
    return Color.White
}