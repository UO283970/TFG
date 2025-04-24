package com.example.tfg.ui.bookDetails.reviews.creation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tfg.R
import com.example.tfg.ui.common.RatingDialog
import com.example.tfg.ui.common.navHost.BookNavigationItems
import com.example.tfg.ui.lists.listDetails.components.TopDetailsListBar
import com.example.tfg.ui.theme.TFGTheme

@Composable
fun ReviewCreation(
    returnToLastScreen: () -> Unit,
    navigateToCleanStack: (route: String) -> Unit,
    reviewCreationViewModel: ReviewCreationViewModel = hiltViewModel()
) {

    LaunchedEffect(reviewCreationViewModel.creationState.reviewCreated) {
        if (reviewCreationViewModel.creationState.reviewCreated) {
            navigateToCleanStack(BookNavigationItems.ReviewScreen.route)
        }
    }

    TFGTheme(dynamicColor = false) {
        Scaffold(
            topBar = {
                TopDetailsListBar(
                    returnToLastScreen = returnToLastScreen,
                    tittle = stringResource(R.string.book_details_review_creation)
                )
            }
        ) { innerPadding ->
            Column(modifier = Modifier
                .padding(innerPadding)
                .then(Modifier.padding(start = 10.dp, end = 10.dp))) {
                OutlinedTextField(
                    value = reviewCreationViewModel.creationState.reviewText,
                    onValueChange = { reviewCreationViewModel.changeReviewText(it) },
                    modifier = Modifier
                        .fillMaxHeight(0.4f)
                        .fillMaxWidth()
                )
                Row (modifier = Modifier.clickable{reviewCreationViewModel.toggleRatingMenu()}){
                    Text(stringResource(R.string.book_details_user_rating))
                    if (reviewCreationViewModel.bookState.bookForDetails.userScore == 0) {
                        Icon(painterResource(R.drawable.empty_star), null, modifier = Modifier.size(24.dp))
                        Icon(Icons.Default.Create, null, modifier = Modifier.size(24.dp))
                    } else {
                        Row {
                            Text(reviewCreationViewModel.bookState.bookForDetails.userScore.toString())
                            Icon(painterResource(R.drawable.full_star), null, modifier = Modifier.size(24.dp))
                            Icon(Icons.Default.Create, null, modifier = Modifier.size(24.dp))
                        }
                    }
                }
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
                    Button({ reviewCreationViewModel.saveReview() }) {
                        Text(stringResource(R.string.save_button))
                    }
                }
                if (reviewCreationViewModel.creationState.ratingMenuOpen) {
                    RatingDialog(
                        MaterialTheme.colorScheme.onPrimary,
                        reviewCreationViewModel.creationState.userScore,
                        { reviewCreationViewModel.toggleRatingMenu() },
                        { reviewCreationViewModel.saveRating() },
                        { reviewCreationViewModel.changeUserScore(it) },
                        { reviewCreationViewModel.changeDeleted(it) }
                    )
                }
            }
        }
    }
}