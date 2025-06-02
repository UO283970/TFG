package com.example.tfg.ui.bookDetails.reviews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tfg.R
import com.example.tfg.model.user.userActivities.ReviewActivity
import com.example.tfg.ui.bookDetails.components.DescriptionText
import com.example.tfg.ui.common.LoadingProgress
import com.example.tfg.ui.common.UserPictureWithoutCache
import com.example.tfg.ui.common.navHost.BookNavigationItems
import com.example.tfg.ui.lists.listDetails.components.TopDetailsListBar
import com.example.tfg.ui.profile.components.statistics.followers.AcceptOperationDialog
import com.example.tfg.ui.theme.TFGTheme
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun ReviewsScreen(returnToLastScreen: () -> Unit, navigateTo: (route: String) -> Unit, reviewsScreenViewModel: ReviewsScreenViewModel = hiltViewModel()) {

    LaunchedEffect(reviewsScreenViewModel.bookReviewState.loadInfo) {
        if (reviewsScreenViewModel.bookReviewState.loadInfo && reviewsScreenViewModel.bookState.bookForDetails.listOfReviews.isEmpty()) {
            navigateTo(BookNavigationItems.ReviewCreationScreen.route)
        }
    }

    if (reviewsScreenViewModel.bookReviewState.loadInfo) {
        TFGTheme(dynamicColor = false) {
            Scaffold(topBar = {
                TopDetailsListBar(returnToLastScreen, stringResource(R.string.book_details_review_screen))
            }) { innerPadding ->
                Box {
                    LazyColumn(
                        modifier = Modifier
                            .padding(innerPadding)
                            .then(Modifier.padding(start = 10.dp, end = 10.dp))
                    ) {
                        items(reviewsScreenViewModel.bookState.bookForDetails.listOfReviews) {
                            Column(verticalArrangement = Arrangement.spacedBy(15.dp), modifier = Modifier.padding(bottom = 15.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                    val signatureKey = remember { mutableStateOf(System.currentTimeMillis().toString()) }
                                    UserPictureWithoutCache(
                                        it.user.profilePicture, signatureKey, Modifier
                                            .size(50.dp)
                                            .clip(CircleShape)
                                    )
                                    Column(modifier = Modifier.weight(1f)){
                                        Text(it.user.userAlias, overflow = TextOverflow.Ellipsis)
                                        Text(it.creationDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.getDefault())))
                                    }
                                    if (reviewsScreenViewModel.mainUserState.getMainUser()?.userId == it.user.userId) {
                                        Row(horizontalArrangement = Arrangement.End) {
                                            Column {
                                                IconButton({ reviewsScreenViewModel.toggleMenu() }) {
                                                    Icon(Icons.Default.MoreVert, null)
                                                }
                                                DropdownMenu(
                                                    expanded = reviewsScreenViewModel.bookReviewState.menuOpen,
                                                    onDismissRequest = { reviewsScreenViewModel.toggleMenu() }) {
                                                    DropdownMenuItem({ Text(stringResource(R.string.delete_review_menu_item)) },
                                                        { reviewsScreenViewModel.toggleDeleteDialogOpen() })
                                                }
                                            }
                                        }
                                    }
                                    var actualReview = it
                                    if(reviewsScreenViewModel.bookReviewState.deleteDialogOpen){
                                        AcceptOperationDialog(
                                            stringResource(R.string.delete_review_dialog),
                                            close = { reviewsScreenViewModel.toggleDeleteDialogOpen() },
                                            accept = {reviewsScreenViewModel.deleteReview(actualReview)}
                                        )
                                    }
                                }
                                ReviewText(it)
                            }
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 15.dp, end = 15.dp),
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        if (reviewsScreenViewModel.bookState.bookForDetails.listOfReviews
                                .find { it.user.userId == reviewsScreenViewModel.mainUserState.getMainUser()?.userId } == null
                        ) {
                            FloatingActionButton(
                                onClick = { navigateTo(BookNavigationItems.ReviewCreationScreen.route) },
                                modifier = Modifier.clip(CircleShape)
                            ) {
                                Icon(Icons.Filled.Add, contentDescription = "")
                            }
                        }
                    }
                }
            }
        }
    } else {
        LoadingProgress()
    }
}

@Composable
private fun ReviewText(activity: ReviewActivity) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.onPrimary)
    ) {
        Column(
            Modifier
                .padding(start = 10.dp, top = 5.dp, end = 10.dp, bottom = 5.dp)
        ) {
            Text(stringResource(R.string.book_details_review_screen_score, if (activity.rating == 0) "-" else activity.rating.toString()))
            DescriptionText(activity.reviewText, 4)
        }
    }
}