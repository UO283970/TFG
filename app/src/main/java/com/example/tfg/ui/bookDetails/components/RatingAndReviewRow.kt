package com.example.tfg.ui.bookDetails.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.tfg.R
import com.example.tfg.ui.bookDetails.BookDetailsViewModel
import com.example.tfg.ui.common.UserPictureWithoutCache
import com.example.tfg.ui.common.navHost.BookNavigationItems
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.Locale

@Composable
fun RatingAndReviewRow(
    viewModel: BookDetailsViewModel,
    navigateTo: (String) -> Unit,
    userScore: Int,
    meanScore: Double,
    totalRatings: Int,
    listOfUserProfilePicturesForReviews: ArrayList<String>,
    numberOfReviews: Int
) {
    Row(
        modifier = Modifier.Companion
            .padding(bottom = 10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.Companion.CenterVertically
    ) {
        Column(horizontalAlignment = Alignment.Companion.CenterHorizontally, modifier = Modifier.Companion.clickable { viewModel.toggleRatingMenu() }) {
            Text(stringResource(R.string.book_details_user_rating))
            if (userScore == 0) {
                Icon(painterResource(R.drawable.empty_star), null, modifier = Modifier.Companion.size(24.dp))
            } else {
                Row {
                    Text(userScore.toString())
                    Icon(painterResource(R.drawable.full_star), null, modifier = Modifier.Companion.size(24.dp))
                }
            }
        }
        Column(horizontalAlignment = Alignment.Companion.CenterHorizontally) {
            Text(stringResource(R.string.book_details_user_mean_rating))
            Row {
                val df = DecimalFormat("#.##", DecimalFormatSymbols(Locale.getDefault()))
                Text(df.format(meanScore / totalRatings).toString())
                Icon(painterResource(R.drawable.full_star), null, modifier = Modifier.Companion.size(24.dp))
                Text(
                    "(" + NumberFormat.getInstance(Locale.getDefault())
                        .format(totalRatings) + ")"
                )
            }
        }
        if (numberOfReviews != 0) {
            Column(horizontalAlignment = Alignment.Companion.CenterHorizontally, modifier = Modifier.Companion.clickable {
                navigateTo(BookNavigationItems.ReviewScreen.route)
            }) {
                Text(stringResource(R.string.book_details_number_reviews, numberOfReviews))
                Row(
                    horizontalArrangement = Arrangement.spacedBy((-12.5).dp),
                    verticalAlignment = Alignment.Companion.CenterVertically
                ) {
                    var count = listOfUserProfilePicturesForReviews.size
                    for (user in listOfUserProfilePicturesForReviews) {
                        val signatureKey = remember { mutableStateOf(System.currentTimeMillis().toString()) }
                        UserPictureWithoutCache(
                            user, signatureKey, Modifier.Companion
                                .size(25.dp)
                                .clip(CircleShape)
                                .zIndex(count.toFloat())
                        )
                        count--
                    }
                }
            }
        } else {
            Column(horizontalAlignment = Alignment.Companion.CenterHorizontally, modifier = Modifier.Companion.clickable {
                navigateTo(BookNavigationItems.ReviewCreationScreen.route)
            }) {
                Text(stringResource(R.string.book_details_no_reviews))
                Icon(Icons.Default.Add, null, modifier = Modifier.Companion.size(24.dp))
            }
        }
    }
}