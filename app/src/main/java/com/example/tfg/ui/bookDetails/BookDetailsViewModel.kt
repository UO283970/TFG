package com.example.tfg.ui.bookDetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.tfg.R
import com.example.tfg.model.Book
import com.example.tfg.ui.common.StringResourcesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject

data class BookDetails(
    var book: Book,
    var totalPages: String = "0",
    var inListButtonString: String,
    var dialogOpened: Boolean = false,
    var selectedBookList: String = "",
    var ratingMenuOpen: Boolean = false,
    var bookUserRating: String = "-"
)

@HiltViewModel
class BookDetailsViewModel @Inject constructor(
    val stringResourcesProvider: StringResourcesProvider
) : ViewModel() {
    var bookInfo by mutableStateOf(
        BookDetails(
            book = getBookInfo(),
            inListButtonString = stringResourcesProvider.getString(R.string.book_add_to_list)
        )
    )

    override fun onCleared() {
        super.onCleared()
        //TODO: Guardar las páginas, si el campo esta vacío guardar a 0
    }

    fun toggleDialog(){
        bookInfo = bookInfo.copy(dialogOpened = !bookInfo.dialogOpened)
    }

    fun toggleRatingMenu(){
        bookInfo = bookInfo.copy(ratingMenuOpen = !bookInfo.ratingMenuOpen)
    }

    fun changePagesRead(pages: String) {
        val pattern =
            Regex("(" + bookInfo.book.pages + ")" + "|(0)|" + "[1-9]{1," + bookInfo.book.pages.toString().length + "}")
        if (pages == "") {
            bookInfo = bookInfo.copy(totalPages = "")
            return
        }
        if (pages.matches(pattern) && (pages.toInt()) <= bookInfo.book.pages) {
            bookInfo = bookInfo.copy(totalPages = pages)
            if (pages.toInt() != 0 && bookInfo.selectedBookList == "") {
                val readingList = stringResourcesProvider.getString(R.string.reading_list_name)
                bookInfo =
                    bookInfo.copy(inListButtonString = readingList)
            }
        }
    }

    fun saveChanges(bookList: String, state: Boolean) {
        if (bookList != "" && state) {
            bookInfo = bookInfo.copy(inListButtonString = bookList)
            if (bookList == stringResourcesProvider.getString(R.string.read_list_name)) {
                bookInfo = bookInfo.copy(totalPages = bookInfo.book.pages.toString())
            }
        } else {
            bookInfo = bookInfo.copy(inListButtonString = stringResourcesProvider.getString(R.string.book_add_to_list))
            bookInfo = bookInfo.copy(selectedBookList = "")
            bookInfo = bookInfo.copy(totalPages = "0")
        }
    }

    fun onDoneChangePages() {
        if (bookInfo.totalPages == "") {
            bookInfo = bookInfo.copy(totalPages = "0")
        }
    }

    private fun getBookInfo(): Book {
        //TODO: Obtener la info del libro
        return Book(
            "Words Of Radiance",
            "Brandon Sanderson",
            R.drawable.prueba,
            pages = 789,
            publicationDate = LocalDate.ofYearDay(2017, 12)
        )
    }
}