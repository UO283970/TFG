package com.example.tfg.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tfg.R
import com.example.tfg.ui.search.components.SearchForEnum

@Composable
fun BigTittleText(text: String) {
    Text(
        text = text,
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        overflow = TextOverflow.Ellipsis,
        maxLines = 2
    )
}

@Composable
fun SmallTittleText(text: String) {
    Text(
        text = text,
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold
    )
}


@Composable
fun TittleBigText(mainTittle: String) {
    Text(
        text = mainTittle,
        fontSize = 24.sp
    )
}

@Composable
fun DescText(minLinesLength : Int, text: String) {
    var expandedState by remember { mutableStateOf(false) }
    var showReadMoreButtonState by remember { mutableStateOf(false) }
    val maxLines = if (expandedState) 200 else minLinesLength

    Column(modifier = Modifier.padding(10.dp)) {
        Text(text = text,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f),
            fontWeight = FontWeight.SemiBold,
            overflow = TextOverflow.Ellipsis,
            maxLines = maxLines,
            onTextLayout = { textLayoutResult: TextLayoutResult ->
                if (textLayoutResult.lineCount > minLinesLength - 1) {
                    if (textLayoutResult.isLineEllipsized(minLinesLength - 1)) showReadMoreButtonState = true
                }
            }
        )
        if (showReadMoreButtonState) {
            Text(
                text = if (expandedState) stringResource(R.string.read_less) else stringResource(R.string.read_more),
                modifier = Modifier.clickable {
                    expandedState = !expandedState
                },
                overflow = TextOverflow.Ellipsis,
            )
        }
    }

}

@Composable
fun ErrorText(errorText : String) {
    Text(
        text = errorText,
        color = MaterialTheme.colorScheme.error,
        style = MaterialTheme.typography.bodySmall,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
fun BookAuthorText(author: String, navigateToSearch: (author: String, searchFor: String) -> Unit) {
    Text(
        author,
        overflow = TextOverflow.Ellipsis,
        fontSize = 16.sp,
        textDecoration = TextDecoration.Companion.Underline,
        maxLines = 1,
        modifier = Modifier.clickable{navigateToSearch(author, SearchForEnum.AUTHOR.toString())}
    )
}

@Composable
fun BookTittleText(tittle: String) {
    Text(
        tittle,
        overflow = TextOverflow.Ellipsis,
        fontSize = 16.sp,
        maxLines = 3,
    )
}