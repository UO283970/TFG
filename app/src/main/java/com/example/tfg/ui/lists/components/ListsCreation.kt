package com.example.tfg.ui.lists.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.tfg.R
import com.example.tfg.model.BookList
import com.example.tfg.ui.lists.ListNavigationItems

@Composable
fun creteOwnLists(navController: NavHostController, ownLists: List<BookList>) {
    for (o in ownLists) {
        listItem(navController, o.listName, 999)
    }
}

@Composable
fun creteDefaultLists(navController: NavHostController) {
    val defaultLists = stringArrayResource(id = R.array.list_of_default_lists)

    for (l in defaultLists) {
        listItem(navController, l, 0)
    }
}

@Composable
fun listItem(navController: NavHostController, listName: String, listTotal: Int) {

    Column(modifier = Modifier.clickable {
        navController.navigate(ListNavigationItems.ListDetails.route + "/Leyendo")
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
                    modifier = Modifier.weight(1f),
                    text = listName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "(999+)",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}