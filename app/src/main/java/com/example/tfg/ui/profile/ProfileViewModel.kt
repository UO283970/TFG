package com.example.tfg.ui.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tfg.R
import com.example.tfg.model.Book
import com.example.tfg.model.booklist.BookList
import com.example.tfg.model.user.User
import com.example.tfg.model.user.userActivities.Activity
import com.example.tfg.repository.GlobalErrorHandler
import com.example.tfg.repository.UserRepository
import com.example.tfg.repository.exceptions.AuthenticationException
import com.example.tfg.ui.common.StringResourcesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

data class ProfileMainState(
    val user: User? = null,
    var profileDefaultLists: ArrayList<BookList> = arrayListOf(),
    var profileBookLists: ArrayList<BookList> = arrayListOf(),
    var profileReviews: ArrayList<Activity> = arrayListOf(),
    var infoLoaded: Boolean = false
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val stringResourcesProvider: StringResourcesProvider,
    private val userRepository: UserRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var profileInfo by mutableStateOf(
        ProfileMainState()
    )

    init {
        val user = savedStateHandle.get<User>("connectedUserInfo")
        viewModelScope.launch {
            if (user == null) {
                try {
                    val connectedUser = userRepository.getAuthenticatedUserInfo()
                    if (connectedUser != null) {
                        val userObtained = connectedUser
                        savedStateHandle["connectedUserInfo"] = userObtained
                        profileInfo = profileInfo.copy(user = userObtained)
                        profileInfo = profileInfo.copy(infoLoaded = true)
                    }
                } catch (e: AuthenticationException) {
                    GlobalErrorHandler.handle(e)
                }
            }else{
                profileInfo = profileInfo.copy(infoLoaded = true)
            }
        }
    }


    private fun obtainUserInfo(user: User?): User? {
        /*TODO: Se le pasa la id del usuario o el token y se obtiene la info*/
        return user
    }


    private fun getUsersProfileLists(user: User?): ArrayList<BookList> {
        /*TODO: Conseguir las listas del usuario*/
        val forTest = Book(
            "Words Of Radiance",
            "Brandon Sanderson",
            R.drawable.prueba,
            pages = 789,
            publicationDate = LocalDate.ofYearDay(2017, 12)
        )

        return arrayListOf(BookList("", "Fantasia interesante", books = arrayListOf(forTest)))
    }

    private fun profileDefaultLists(user: User?): ArrayList<BookList> {
        /*TODO: Conseguir las listas por defecto del usuario*/
        val listNames = stringResourcesProvider.getStringArray(R.array.list_of_default_lists)
        val listOfBooks: ArrayList<BookList> = arrayListOf()
        val forTest = Book(
            "Words Of Radiance",
            "Brandon Sanderson",
            R.drawable.prueba,
            pages = 789,
            publicationDate = LocalDate.ofYearDay(2017, 12)
        )

        for (name in listNames) {
            listOfBooks.add(BookList("", name, books = arrayListOf(forTest)))
        }

        return listOfBooks
    }
}
