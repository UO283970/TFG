package com.example.tfg.ui.lists.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tfg.R
import com.example.tfg.model.BookList
import com.example.tfg.ui.lists.ListScreenEvent
import com.example.tfg.ui.lists.ListViewModel

@Composable
fun creteOwnLists(viewModel: ListViewModel) {
    Column {
        Box{
            LazyColumn {
                items(viewModel.listState.ownLists) {
                    listItem(viewModel, it)
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 15.dp, end = 15.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Bottom
            ) {
                FloatingActionButton(onClick = { /*TODO*/ }, modifier = Modifier.clip(CircleShape)) {
                    Icon(Icons.Filled.Add, contentDescription = "")
                }
            }
        }
    }
}

@Composable
fun creteDefaultLists(viewModel: ListViewModel) {
    LazyColumn {
        items(viewModel.listState.defaultLists) {
            listItem(viewModel, it)
        }
    }
}

@Composable
fun listItem(viewModel: ListViewModel, list: BookList) {
    Column(modifier = Modifier.clickable {
        viewModel.onEvent(ListScreenEvent.ListDetails(list))
    }) {
        HorizontalDivider()
        Row(
            modifier = Modifier.padding(start = 20.dp, bottom = 10.dp, top = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painterResource(R.drawable.prueba),
                contentDescription = stringResource(id = R.string.home_imageDescNoBooks),
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .height(100.dp)
            )
            Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                Text(
                    modifier = Modifier.weight(1f, false),
                    text = list.listName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "(" + list.books.size + ")",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}