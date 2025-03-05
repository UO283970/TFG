package com.example.tfg.ui.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tfg.R
import com.example.tfg.ui.common.tittleBigText

@Composable
fun statistics() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        statisticTexts(stringResource(R.string.profile_rating_text)) { /*TODO*/ }
        statisticTexts(stringResource(R.string.profile_review_text)) { /*TODO*/ }
        statisticTexts(stringResource(R.string.profile_following_text)) { /*TODO*/ }
        statisticTexts(stringResource(R.string.profile_followers_text)) { /*TODO*/ }
    }
}

@Composable
private fun statisticTexts(mainText: String, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick })
    {
        Text(1.toString(), fontWeight = FontWeight.SemiBold)
        Text(
            mainText,
            fontWeight = FontWeight.SemiBold, fontSize = 14.sp
        )
    }
}

@Composable
fun mainUserProfileInfo() {
    Row() {
        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            profileImage()
            userNameAndDate()
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Outlined.Settings, contentDescription = "")
        }
    }
}

@Composable
fun profileImage() {
    Image(
        painterResource(R.drawable.prueba),
        contentDescription = "",
        contentScale = ContentScale.FillBounds,
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
    )
}

@Composable
fun userNameAndDate() {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            "Nombre de Usuario",
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Row() {
            Icon(
                Icons.Outlined.DateRange,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
            )
            Text(
                stringResource(R.string.profile_joined) + "enero 2025",
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun profileLists() {

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        profileLists(stringArrayResource(R.array.list_of_default_lists), stringResource(R.string.profile_default_lists))
        profileLists(stringArrayResource(R.array.list_of_default_lists), stringResource(R.string.profile_own_lists))
    }
}

@Composable
fun profileLists(lists : Array<String>, tittle: String) {

    Column {
        tittleBigText(tittle)
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            for (d in lists) {
                Column(
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .wrapContentWidth(),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    profileNameList(d)
                }
            }
        }
    }
}

@Composable
fun profileNameList(tittle: String) {
    profileListImage()
    Row(
        Modifier.widthIn(0.dp, 120.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        profileListText(
            tittle,
            Modifier
                .wrapContentWidth()
                .widthIn(0.dp, 65.dp)
        )
        Text(
            text = ("(" + 999.toString() + "+)"),
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1
        )
    }
}

@Composable
fun profileListImage() {
    Image(
        painterResource(R.drawable.prueba),
        contentDescription = stringResource(id = R.string.home_imageDescNoBooks),
        modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .width(110.dp)
            .height(160.dp),
        contentScale = ContentScale.FillBounds

    )
}

@Composable
fun profileListText(text: String, modifier: Modifier) {
    Text(
        text = text,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        modifier = modifier
    )
}