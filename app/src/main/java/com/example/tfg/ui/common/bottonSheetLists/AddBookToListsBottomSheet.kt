package com.example.tfg.ui.common.bottonSheetLists

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.rememberLottieDynamicProperties
import com.airbnb.lottie.compose.rememberLottieDynamicProperty
import com.example.tfg.R
import com.example.tfg.model.Book
import com.example.tfg.model.booklist.DefaultList
import com.example.tfg.model.booklist.ListsState
import com.example.tfg.repository.ListRepository
import com.example.tfg.ui.common.StringResourcesProvider
import com.example.tfg.ui.common.navHost.ListNavigationItems

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBookToListsBottomSheet(
    mainColor: Color,
    textColor: Color,
    stringResourcesProvider: StringResourcesProvider,
    listsRepository: ListRepository,
    book: Book,
    listsState: ListsState,
    changeCloseOpen: () -> Unit,
    onDefaultListChange: (DefaultList, Boolean) -> Unit,
    navigateTo: (String) -> Unit,
    viewModel: AddBookToListsBottomSheetViewModel = AddBookToListsBottomSheetViewModel(stringResourcesProvider, listsRepository,book, listsState)
) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.add_animation)
    )
    val dynamicProperties = rememberLottieDynamicProperties(
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR_FILTER,
            value = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                textColor.hashCode(),
                BlendModeCompat.SRC_ATOP
            ),
            keyPath = arrayOf("**")
        )
    )
    val sheetState = rememberModalBottomSheetState()
    val state = viewModel.sheetListSate.collectAsStateWithLifecycle()
    val localFocus = LocalFocusManager.current

    ModalBottomSheet(
        {
            changeCloseOpen()
            viewModel.onClose()
        },
        containerColor = mainColor,
        contentColor = textColor,
        sheetState = sheetState
    ) {
        Column(Modifier.Companion.padding(start = 10.dp, end = 10.dp)) {
            for (list in state.value.checkboxDefaultList) {
                Row(verticalAlignment = Alignment.Companion.CenterVertically) {
                    Text(
                        list.key.getName(), modifier = Modifier.Companion.weight(1f)
                    )
                    Checkbox(
                        checked = list.value,
                        onCheckedChange = {
                            localFocus.clearFocus()
                            onDefaultListChange(list.key,!list.value)
                            viewModel.changeSelectedDefaultList(list.key, !list.value)
                        })
                }
            }
        }
        HorizontalDivider(Modifier.Companion.padding(start = 10.dp, end = 10.dp))
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.Companion.fillMaxWidth()) {
            Text(stringResource(R.string.book_add_new_list_my_lists), fontSize = 22.sp)
        }
        LazyColumn(
            modifier = Modifier.Companion
                .weight(1f)
                .padding(start = 10.dp, end = 10.dp)
        ) {
            for (list in state.value.checkboxUserList) {
                item {
                    var animationState by remember { mutableStateOf(false) }
                    val startAnimation = rememberLottieAnimatable()
                    var midState by remember { mutableStateOf(list.value) }

                    LaunchedEffect(midState) {
                        val minProgress = if (midState) 0f else 0.5f
                        val maxProgress = if (midState) 0.5f else 1f

                        if (midState) {
                            startAnimation.snapTo(
                                composition,
                                0.5f
                            )
                        }

                        if (!animationState) return@LaunchedEffect
                        startAnimation.animate(
                            composition = composition,
                            clipSpec = LottieClipSpec.Progress(minProgress, maxProgress),
                            speed = 1f
                        )
                    }

                    Row(verticalAlignment = Alignment.Companion.CenterVertically) {
                        Text(
                            list.key.getName(),
                            modifier = Modifier.Companion.weight(1f)
                        )
                        LottieAnimation(
                            composition,
                            dynamicProperties = dynamicProperties,
                            progress = { startAnimation.progress },
                            modifier = Modifier.Companion
                                .size(38.dp)
                                .padding(top = 5.dp)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) {
                                    midState = !midState
                                    animationState = true
                                    viewModel.changeUserListState(list.key, list.value)
                                },
                        )
                    }
                }
            }
            item {
                Row(
                    modifier = Modifier.Companion
                        .clickable {
                            changeCloseOpen()
                            viewModel.onClose()
                            navigateTo(ListNavigationItems.ListCreation.route)
                        }
                        .padding(top = 10.dp)
                        .fillMaxWidth()) {
                    Icon(Icons.Default.Add, null)
                    Text(stringResource(R.string.book_add_new_list))
                }
            }
        }
    }
}