package com.example.tfg.ui.search.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tfg.R
import com.example.tfg.ui.common.SmallTittleText

@Composable
fun orderByRow() {
    val orderByList: Array<String> = stringArrayResource(id = R.array.list_of_order_by)

    SmallTittleText(stringResource(id = R.string.search_filters_orderBy))
    Row(
        horizontalArrangement = Arrangement.spacedBy(3.dp),
        modifier = Modifier.horizontalScroll(rememberScrollState())
    ) {
        for (s in orderByList) {
            filterButtonOrderBy(s)
        }
    }
}

@Composable
fun filterButtonOrderBy(text: String) {
    val (orderDec, setOrderDec) = remember { mutableStateOf(true) }

    OutlinedButton(
        onClick = {
            setOrderDec(!orderDec)
            /*TODO*/
        },
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.onBackground
        ),
        contentPadding = PaddingValues(start = 10.dp, end = 10.dp)
    ) {
        if (orderDec) {
            Icon(Icons.Default.KeyboardArrowDown, contentDescription = "")
        } else {
            Icon(Icons.Default.KeyboardArrowUp, contentDescription = "")
        }
        Text(text = text)
    }
}