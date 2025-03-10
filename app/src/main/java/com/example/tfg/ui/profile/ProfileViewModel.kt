package com.example.tfg.ui.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.tfg.R
import com.example.tfg.model.Book
import com.example.tfg.model.BookList
import com.example.tfg.model.user.User
import com.example.tfg.model.user.userFollowStates.UserFollowStateUnfollow
import com.example.tfg.model.user.userPrivacy.UserPrivacyPublic
import com.example.tfg.ui.common.StringResourcesProvider
import java.time.LocalDate

sealed class ProfileScreenEvent {
    object EditButtonClick : ProfileScreenEvent()
    object ReviewsButtonClick : ProfileScreenEvent()
    object RatingButtonClick : ProfileScreenEvent()
    object FollowedButtonClick : ProfileScreenEvent()
    object FollowersButtonClick : ProfileScreenEvent()

}

data class ProfileMainState(
    val user: User = User(""),
    var profileBookLists: ArrayList<BookList> = arrayListOf(),

    var profileDefaultLists: ArrayList<BookList> = arrayListOf()
)

class ProfileViewModel(
    private val navController: NavController,
    private val stringResourcesProvider: StringResourcesProvider
) : ViewModel() {
    var profileInfo by mutableStateOf(
        ProfileMainState().copy(
            profileDefaultLists = profileDefaultLists(),
            profileBookLists = getUsersProfileLists(),
            user = obtainUserInfo()
        )
    )


    fun onEvent(event: ProfileScreenEvent) {
        when (event) {
            is ProfileScreenEvent.EditButtonClick -> {
                navController.navigate(""/*Navegar a la pantalla de editar perfil*/)
            }

            is ProfileScreenEvent.RatingButtonClick -> {
                navController.navigate(""/*Navegar a la pantalla de ratings perfil*/)
            }

            is ProfileScreenEvent.ReviewsButtonClick -> {
                navController.navigate(""/*Navegar a la pantalla de reviews perfil*/)
            }

            is ProfileScreenEvent.FollowedButtonClick -> {
                navController.navigate(""/*Navegar a la pantalla de seguidos perfil*/)
            }

            is ProfileScreenEvent.FollowersButtonClick -> {
                navController.navigate(""/*Navegar a la pantalla de seguidores perfil*/)
            }
        }
    }

    private fun obtainUserInfo(): User {
        return User("Nombre de Usuario2", R.drawable.prueba, UserPrivacyPublic(), UserFollowStateUnfollow())
    }


    private fun getUsersProfileLists(): ArrayList<BookList> {
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

    private fun profileDefaultLists(): ArrayList<BookList> {
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