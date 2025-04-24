package com.example.tfg.ui.bookDetails.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.tfg.R

@Composable
fun DescriptionText(desc: String, maxLines: Int) {
    var expandedState by remember { mutableStateOf(false) }
    var showReadMoreButtonState by remember { mutableStateOf(false) }
    val maxLines = if (expandedState) 200 else maxLines

    Column(modifier = Modifier.Companion.padding(bottom = 5.dp)) {
        Text(
            text = desc,
            fontWeight = FontWeight.Companion.SemiBold,
            overflow = TextOverflow.Companion.Ellipsis,
            maxLines = maxLines,
            onTextLayout = { textLayoutResult: TextLayoutResult ->
                if (textLayoutResult.lineCount > maxLines - 1) {
                    if (textLayoutResult.isLineEllipsized(maxLines - 1)) showReadMoreButtonState = true
                }
            }
        )
        if (showReadMoreButtonState) {
            Text(
                text = if (expandedState) stringResource(R.string.read_less) else stringResource(R.string.read_more),
                modifier = Modifier.Companion.clickable {
                    expandedState = !expandedState
                }
            )
        }
    }
}