package com.example.tfg.ui.lists.listDetails.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tfg.R
import com.example.tfg.model.Book
import com.example.tfg.ui.common.bigTittleText
import com.example.tfg.ui.common.smallTittleText

@Composable
fun listDetailsItemList(books: ArrayList<Book>) {
    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .padding(top = 10.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        for(book in books){
            listDetailsItem(book)
        }
    }
}

@Composable
fun listDetailsItem(book: Book) {
    Box(
        modifier = Modifier
            .clip(AlertDialogDefaults.shape)
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.onPrimary)
    ) {
        Column(
            modifier = Modifier
                .padding(top = 10.dp)
        ) {

            Row(modifier = Modifier.padding(start = 20.dp, bottom = 10.dp)) {
                Box(contentAlignment = Alignment.TopEnd) {
                    Image(
                        painterResource(book.coverImage),
                        contentDescription = stringResource(id = R.string.home_imageDescNoBooks),
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .height(140.dp)
                    )
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .padding(end = 5.dp, top = 5.dp)
                            .clip(CircleShape)
                            .background(color = MaterialTheme.colorScheme.onPrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = if (book.userScore == -1) "-" else book.userScore.toString())
                    }
                }

                Column(
                    Modifier.padding(start = 10.dp, top = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    bigTittleText(book.tittle)
                    Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                        smallTittleText(book.publicationDate.year.toString())
                        smallTittleText(if (book.pages != 0) book.pages.toString() + "pg" else "-pg")
                    }
                    bigTittleText(book.author)
                }
            }
        }
    }
}