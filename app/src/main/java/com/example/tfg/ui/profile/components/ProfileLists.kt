package com.example.tfg.ui.profile.components

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.tfg.R
import com.example.tfg.model.booklist.BookList
import com.example.tfg.model.booklist.BookListClass
import com.example.tfg.model.booklist.DefaultList
import com.example.tfg.ui.common.TittleBigText
import com.example.tfg.ui.common.navHost.ListNavigationItems

@Composable
fun ProfileLists(defaultList: List<DefaultList>, userLists: List<BookListClass>, navigateTo: (route: String) -> Unit, goToListDetail: (bookList: BookList) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        ProfileDefaultLists(defaultList, stringResource(R.string.profile_default_lists),navigateTo, goToListDetail)
        ProfileOwnLists(userLists, stringResource(R.string.profile_own_lists), navigateTo, goToListDetail)
    }
}

@Composable
fun ProfileDefaultLists(lists: List<DefaultList>, tittle: String, navigateTo: (route: String) -> Unit, goToListDetail: (bookList: BookList) -> Unit) {
    if(lists.isNotEmpty()){
        Column{
            TittleBigText(tittle)
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(lists){
                    Column(
                        modifier = Modifier
                            .padding(top = 5.dp)
                            .wrapContentWidth().clickable{
                                goToListDetail(it)
                                navigateTo(ListNavigationItems.ListDetails.route)
                            },
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        ProfileNameList(it.getName(), it.numberOfBooks, it.listImage)
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileOwnLists(lists: List<BookListClass>, tittle: String, navigateTo: (route: String) -> Unit, goToListDetail: (bookList: BookList) -> Unit) {
    if(lists.isNotEmpty()){
        Column{
            TittleBigText(tittle)
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                items(lists){
                    Column(
                        modifier = Modifier
                            .padding(top = 5.dp)
                            .wrapContentWidth()
                            .clickable{
                                goToListDetail(it)
                            navigateTo(ListNavigationItems.ListDetails.route)
                        },
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        ProfileNameList(it.getName(),it.numberOfBooks, it.listImage)
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileNameList(name: String, numberOfBooks: Int, listImage: String) {
    ProfileListImage(listImage)
    Row(
        Modifier.widthIn(0.dp, 100.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        ProfileListText(
            name,
            Modifier
                .weight(1f)
        )
        Text(
            text = ("($numberOfBooks)"),
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProfileListImage(firstBook: String) {
    GlideImage(
        model = firstBook,
        contentDescription = stringResource(R.string.book_cover),
        loading = placeholder(R.drawable.no_cover_image_book),
        failure = placeholder(R.drawable.no_cover_image_book),
        transition = CrossFade,
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