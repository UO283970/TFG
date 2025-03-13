package com.example.tfg.ui.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tfg.R
import com.example.tfg.model.booklist.BookList
import com.example.tfg.ui.common.tittleBigText
import com.example.tfg.ui.profile.ProfileViewModel

@Composable
fun profileLists(viewModel: ProfileViewModel) {

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        profileLists(viewModel.profileInfo.profileDefaultLists, stringResource(R.string.profile_default_lists))
        profileLists(viewModel.profileInfo.profileBookLists, stringResource(R.string.profile_own_lists))
    }
}

@Composable
fun profileLists(lists: ArrayList<BookList>, tittle: String) {

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
fun profileNameList(bookList: BookList) {
    profileListImage(/*TODO: Aquí y en todas las listas es necesario obtener la imagen del primer libro sino se pondrá una por defecto*/)
    Row(
        Modifier.widthIn(0.dp, 120.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        profileListText(
            bookList.listName,
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