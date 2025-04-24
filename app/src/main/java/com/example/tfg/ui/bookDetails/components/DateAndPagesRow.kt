package com.example.tfg.ui.bookDetails.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.example.tfg.R
import java.time.LocalDate

@Composable
fun DateAndPagesRow(date: LocalDate, pages: Int, progress: Int) {
    Row {
        if (date != LocalDate.MIN) {
            Text(
                stringResource(R.string.book_details_publish_year, date.year),
                modifier = Modifier.Companion.weight(1f),
                overflow = TextOverflow.Companion.Ellipsis,
                maxLines = 2
            )
        }
        if (pages != 0) {
            if (progress != -1) {
                Text(
                    stringResource(R.string.book_details_pages_with_progress, progress, pages),
                    overflow = TextOverflow.Companion.Ellipsis,
                    maxLines = 2,
                    textAlign = TextAlign.Companion.Center
                )
            } else {
                Text(
                    stringResource(R.string.book_details_pages, pages),
                    overflow = TextOverflow.Companion.Ellipsis,
                    maxLines = 2,
                    textAlign = TextAlign.Companion.Center
                )
            }
        }
    }
}