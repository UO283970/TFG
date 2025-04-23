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
import com.example.tfg.repository.ActivityRepository
import com.example.tfg.repository.BookRepository
import com.example.tfg.repository.ListRepository
import com.example.tfg.repository.UserRepository
import com.example.tfg.ui.common.StringResourcesProvider
import com.graphQL.type.UserActivityType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class BookDetails(
    var userProfilesPictures: ArrayList<String>,
    var ratingMenuOpen: Boolean = false,
    var addToListOpen: Boolean = false,
    var selectedBookList: String = "",
    var bookUserRating: String = "-",
    var inListButtonString: String,
    var deleted: Boolean = false,
    var totalPages: String = "0",
    var loadInfo: Boolean = true,
    var refresh: Boolean = true,
    var userScore: Int,
)

@HiltViewModel
class BookDetailsViewModel @Inject constructor(
    val stringResourcesProvider: StringResourcesProvider,
    val listRepository: ListRepository,
    val bookRepository: BookRepository,
    val userRepository: UserRepository,
    val activityRepository: ActivityRepository,
    val mainUserState: MainUserState,
    val listsState: ListsState,
    val bookState: BookState
) : ViewModel() {
    var bookInfo by mutableStateOf(
        BookDetails(
            inListButtonString =
                if (bookState.bookForDetails.readingState == "") stringResourcesProvider.getString(R.string.book_add_to_list)
                else bookState.bookForDetails.readingState,
            userScore = bookState.bookForDetails.userScore,
            userProfilesPictures = bookState.bookForDetails.listOfUserProfilePicturesForReviews
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

    fun changeUserScore(score: Int){
        bookInfo = bookInfo.copy(userScore = score)
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

    fun changeDeleted(state: Boolean) {
        bookInfo = bookInfo.copy(deleted = state)
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

    fun saveRating(){
        if(bookState.bookForDetails.userScore != bookInfo.userScore){
            viewModelScope.launch {
                val rating = activityRepository.addActivity("",bookInfo.userScore,bookState.bookForDetails.bookId, UserActivityType.RATING)
                if(rating != null){
                    if(bookState.bookForDetails.userScore == 0){
                        bookState.bookForDetails.totalRatings++
                    }
                    if(bookInfo.deleted){
                        bookState.bookForDetails.totalRatings--
                    }
                    bookState.bookForDetails.meanScore = (bookState.bookForDetails.meanScore - bookState.bookForDetails.userScore) + bookInfo.userScore
                    bookState.bookForDetails.userScore = bookInfo.userScore
                    bookInfo = bookInfo.copy(refresh = !bookInfo.refresh)
                }
            }
        }
        toggleRatingMenu()
    }

    fun onDoneChangePages() {
        if (bookInfo.totalPages == "") {
            bookInfo = bookInfo.copy(totalPages = "0")
        }
    }
}