package com.example.tfg.ui.search.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tfg.R
import com.example.tfg.ui.common.SmallTittleText

@Composable
fun filterButtonsSubjectRow() {
    val subjectFilters: Array<String> = stringArrayResource(id = R.array.list_of_subjects)

    SmallTittleText(stringResource(id = R.string.search_filters_filterFor))
    Row(
        horizontalArrangement = Arrangement.spacedBy(3.dp),
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
    ) {
        for (s in subjectFilters) {
            filterButtonSubject(s)
        }
    }
}

@Composable
fun filterButtonSubject(text: String) {
    OutlinedButton(
        onClick = { /*TODO*/ },
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.onBackground
        )
    ) {
        Text(text = text)
    }
}