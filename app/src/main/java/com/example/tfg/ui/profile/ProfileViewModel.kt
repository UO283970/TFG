package com.example.tfg.ui.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.tfg.R
import com.example.tfg.model.Book
import com.example.tfg.model.booklist.BookList
import com.example.tfg.model.user.User
import com.example.tfg.model.user.userActivities.Activity
import com.example.tfg.model.user.userFollowStates.UserFollowStateEnum
import com.example.tfg.model.user.userPrivacy.UserPrivacyLevel
import com.example.tfg.ui.common.StringResourcesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject

data class ProfileMainState(
    val user: User?,
    var profileDefaultLists: ArrayList<BookList>,
    var profileBookLists: ArrayList<BookList>,
    var profileReviews: ArrayList<Activity> = arrayListOf()
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val stringResourcesProvider: StringResourcesProvider,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val userConnected = User(
        /*TODO: Le pasas el usuario que esta conectado porque se accede desde el menú*/
        "Nombre de Usuario2",
        R.drawable.prueba,
        UserPrivacyLevel.PUBLIC,
        UserFollowStateEnum.OWN,
        userName = "Nombre de Usuario2"
    )
    val user = savedStateHandle.get<User>("user")

    var profileInfo by mutableStateOf(
        ProfileMainState(obtainUserInfo(userConnected), profileDefaultLists(userConnected), getUsersProfileLists(userConnected))
    )

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

        return arrayListOf(BookList("Fantasia interesante", arrayListOf(forTest)))
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
            listOfBooks.add(BookList(name, arrayListOf(forTest)))
        }

        return listOfBooks
    }
}
