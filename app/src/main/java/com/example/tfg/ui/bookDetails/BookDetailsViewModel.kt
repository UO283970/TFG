package com.example.tfg.ui.bookDetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tfg.R
import com.example.tfg.model.book.BookState
import com.example.tfg.model.booklist.DefaultList
import com.example.tfg.model.booklist.ListsState
import com.example.tfg.model.user.MainUserState
import com.example.tfg.model.user.User
import com.example.tfg.repository.BookRepository
import com.example.tfg.repository.ListRepository
import com.example.tfg.repository.UserRepository
import com.example.tfg.ui.common.StringResourcesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class BookDetails(
    var totalPages: String = "0",
    var inListButtonString: String,
    var addToListOpen: Boolean = false,
    var selectedBookList: String = "",
    var ratingMenuOpen: Boolean = false,
    var bookUserRating: String = "-",
    var loadInfo: Boolean = true,
)

@HiltViewModel
class BookDetailsViewModel @Inject constructor(
    val stringResourcesProvider: StringResourcesProvider,
    val bookState: BookState,
    val listRepository: ListRepository,
    val listsState: ListsState,
    val bookRepository: BookRepository,
    val userRepository: UserRepository,
    val mainUserState: MainUserState
) : ViewModel() {
    var bookInfo by mutableStateOf(
        BookDetails(
            inListButtonString =
                if (bookState.bookForDetails.readingState == "") stringResourcesProvider.getString(R.string.book_add_to_list)
                else bookState.bookForDetails.readingState
        )
    )

    init {
        getExtraInfo()
    }

    private fun getExtraInfo() {
        viewModelScope.launch {
            val extraInfo = bookRepository.getExtraInfoForBook(bookState.bookForDetails.bookId)
            val minInfoUser = userRepository.getMinUserInfo()
            if(extraInfo != null && minInfoUser != null){
                bookState.bookForDetails.userScore = extraInfo.userScore
                bookState.bookForDetails.meanScore = extraInfo.meanScore
                bookState.bookForDetails.totalRatings = extraInfo.numberOfRatings
                bookState.bookForDetails.userProgression = extraInfo.progress
                bookState.bookForDetails.numberOfReviews = extraInfo.numberOfReviews
                bookState.bookForDetails.listOfUserProfilePicturesForReviews = ArrayList(extraInfo.userProfilePictures)
                mainUserState.setMainUser(User(
                    userAlias = minInfoUser.userAlias,
                    profilePicture = minInfoUser.profilePictureURL,
                    userName = minInfoUser.userName,
                    userId = minInfoUser.userId
                ))
                finishLoad()
            }
        }
    }

    fun openAddList() {
        bookInfo = bookInfo.copy(addToListOpen = !bookInfo.addToListOpen)
    }

    fun toggleRatingMenu() {
        bookInfo = bookInfo.copy(ratingMenuOpen = !bookInfo.ratingMenuOpen)
    }

    fun finishLoad() {
        bookInfo = bookInfo.copy(loadInfo = false)
    }

    fun changePagesRead(pages: String) {
        val pattern =
            Regex("(" + bookState.bookForDetails.pages + ")" + "|(0)|" + "[1-9]{1," + bookState.bookForDetails.pages.toString().length + "}")
        if (pages == "") {
            bookInfo = bookInfo.copy(totalPages = "")
            return
        }
        if (pages.matches(pattern) && (pages.toInt()) <= bookState.bookForDetails.pages) {
            bookInfo = bookInfo.copy(totalPages = pages)
            if (pages.toInt() != 0 && bookInfo.selectedBookList == "") {
                val readingList = stringResourcesProvider.getString(R.string.reading_list_name)
                bookInfo =
                    bookInfo.copy(inListButtonString = readingList)
            }
        }
    }

    fun saveChanges(bookList: DefaultList, state: Boolean) {
        if (state) {
            bookInfo = bookInfo.copy(inListButtonString = bookList.listName)
            if (bookList.listName == stringResourcesProvider.getString(R.string.read_list_name)) {
                bookInfo = bookInfo.copy(totalPages = bookState.bookForDetails.pages.toString())
                bookState.bookForDetails.userProgression = bookState.bookForDetails.pages
            }
            bookState.bookForDetails.userProgression = if (bookState.bookForDetails.userProgression == -1) 0 else bookState.bookForDetails.userProgression
        } else {
            bookState.bookForDetails.userProgression = -1
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
}