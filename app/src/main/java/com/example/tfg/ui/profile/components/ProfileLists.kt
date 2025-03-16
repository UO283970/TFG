package com.example.tfg.ui.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.example.tfg.model.Book
import com.example.tfg.model.booklist.BookList
import com.example.tfg.ui.common.tittleBigText

@Composable
fun ProfileLists(defaultList: ArrayList<BookList>,userLists: ArrayList<BookList>) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        ProfileLists(defaultList, stringResource(R.string.profile_default_lists))
        ProfileLists(userLists, stringResource(R.string.profile_own_lists))
    }
}

@Composable
fun ProfileLists(lists: ArrayList<BookList>, tittle: String) {

    Column {
        tittleBigText(tittle)
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(lists){
                Column(
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .wrapContentWidth(),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    ProfileNameList(it)
                }
            }
        }
    }
}

@Composable
fun ProfileNameList(bookList: BookList) {
    ProfileListImage(bookList.books[0]/*TODO: Aquí y en todas las listas es necesario obtener la imagen del primer libro sino se pondrá una por defecto*/)
    Row(
        Modifier.widthIn(0.dp, 120.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        ProfileListText(
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
fun ProfileListImage(firstBook: Book) {
    Image(
        painterResource(firstBook.coverImage),
        contentDescription = stringResource(id = R.string.home_imageDescNoBooks),
        modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .width(110.dp)
            .height(160.dp),
        contentScale = ContentScale.FillBounds

    )
}

@Composable
fun ProfileListText(text: String, modifier: Modifier) {
    Text(
        text = text,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        modifier = modifier
    )
}