package com.example.tfg.ui.lists.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalWindowInfo
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
import com.example.tfg.ui.common.navHost.ListNavigationItems
import com.example.tfg.ui.lists.ListMainState
import com.example.tfg.ui.lists.ListViewModel

@Composable
fun CreteOwnLists(
    viewModel: ListViewModel,
    navigateTo: (route: String) -> Unit,
    state: ListMainState,
) {
    Column {
        Box {
            LazyColumn {
                items(state.listsState.getOwnLists()) {
                    NewListItem(viewModel,navigateTo, it, it.listImage)
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 15.dp, end = 15.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Bottom
            ) {
                if(state.canAddLists){
                    FloatingActionButton(
                        onClick = { navigateTo(viewModel.listCreation()) },
                        modifier = Modifier.clip(CircleShape)
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = "")
                    }
                }
            }
        }
    }
}

@Composable
fun CreteDefaultLists(
    state: ListMainState,
    navigateTo: (route: String) -> Unit,
    viewModel: ListViewModel
) {
    LazyColumn {
        items(state.listsState.getDefaultLists()) {
            NewListItem(viewModel, navigateTo,it, it.listImage)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NewListItem(viewModel: ListViewModel, navigateTo: (route: String) -> Unit, bookList: BookList, listImage: String) {
    val constraints = LocalWindowInfo.current.containerSize.height.dp
    Box(
        Modifier
            .padding(start = 10.dp, top = 5.dp, end = 10.dp, bottom = 5.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.onPrimary)
            .clickable {
                viewModel.listDetails(bookList)
                navigateTo(ListNavigationItems.ListDetails.route)
            }
    ) {
        Row(
            Modifier
                .padding(start = 10.dp, top = 5.dp, end = 10.dp, bottom = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            GlideImage(
                model = listImage,
                contentDescription = stringResource(R.string.book_cover),
                loading = placeholder(R.drawable.no_cover_image_book),
                failure = placeholder(R.drawable.no_cover_image_book),
                transition = CrossFade,
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.17f)
                    .height(constraints * 0.05f)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.FillBounds
            )
            Column(Modifier.fillMaxWidth()) {
                Text(
                    text = bookList.getName(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = bookList.getBookCount().toString() + " " + stringResource(R.string.list_text_books),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}