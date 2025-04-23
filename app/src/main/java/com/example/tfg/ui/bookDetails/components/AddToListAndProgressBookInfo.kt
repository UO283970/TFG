package com.example.tfg.ui.bookDetails.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tfg.R
import com.example.tfg.ui.bookDetails.BookDetailsViewModel

@Composable
fun AddToListAndProgressBookInfo(
    focus: FocusManager,
    viewModel: BookDetailsViewModel,
    color: Color,
    textColor: Color
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Button(
            {
                focus.clearFocus(true)
            },
            modifier = Modifier.padding(end = 10.dp).fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(color)
        ) {
            if(viewModel.bookInfo.inListButtonString == stringResource(R.string.book_add_to_list)){
                Icon(Icons.Default.Add, null, tint = textColor)
            }
            Text(viewModel.bookInfo.inListButtonString, color = textColor)
        }
//        Row(
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(stringResource(R.string.book_pages))
//            OutlinedTextField(
//                value = viewModel.bookInfo.totalPages,
//                onValueChange = { viewModel.changePagesRead(it) },
//                modifier = Modifier
//                    .weight(1f)
//                    .onFocusChanged {
//                        if (it.isFocused && viewModel.bookInfo.totalPages == "0") {
//                            viewModel.changePagesRead("")
//                        }
//                    },
//                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Companion.End),
//                singleLine = true,
//                colors = OutlinedTextFieldDefaults.colors(
//                    unfocusedBorderColor = Color.Companion.Transparent,
//                    focusedBorderColor = Color.Companion.Transparent
//                ),
//                keyboardOptions = KeyboardOptions(
//                    keyboardType = KeyboardType.Companion.Number,
//                    imeAction = ImeAction.Companion.Done
//                ),
//                keyboardActions =
//                    KeyboardActions(
//                        onDone = {
//                            viewModel.onDoneChangePages()
//                            focus.clearFocus()
//                        }
//                    )
//            )
//            Text("/" + viewModel.bookInfo.book.pages.toString())
//        }
    }
}