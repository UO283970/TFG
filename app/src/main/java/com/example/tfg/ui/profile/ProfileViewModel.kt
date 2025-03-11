package com.example.tfg.ui.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.tfg.R
import com.example.tfg.model.Book
import com.example.tfg.model.BookList
import com.example.tfg.model.user.Activity
import com.example.tfg.model.user.User
import com.example.tfg.model.user.userActivities.ReviewActivity
import com.example.tfg.model.user.userFollowStates.UserFollowStateFollowed
import com.example.tfg.model.user.userFollowStates.UserFollowStateUnfollow
import com.example.tfg.model.user.userPrivacy.UserPrivacyPublic
import com.example.tfg.ui.common.CommonEventHandler
import com.example.tfg.ui.common.StringResourcesProvider
import com.example.tfg.ui.common.navHost.ProfileNavigationItems
import java.time.LocalDate

sealed class ProfileScreenEvent {
    object EditButtonClick : ProfileScreenEvent()
    object FollowButtonClick : ProfileScreenEvent()
    object PendingButtonClick : ProfileScreenEvent()
    object UnFollowButtonClick : ProfileScreenEvent()
    object ReviewsButtonClick : ProfileScreenEvent()
    object FollowedButtonClick : ProfileScreenEvent()
    object FollowersButtonClick : ProfileScreenEvent()
    data class ChangeUserName(val userName: String) : ProfileScreenEvent()
    data class ChangeUserDescription(val userDescription: String) : ProfileScreenEvent()
    data class ChangeUserAlias(val userAlias: String) : ProfileScreenEvent()

}

data class ProfileMainState(
    val commonEventHandler: CommonEventHandler,
    val user: User,
    var profileBookLists: ArrayList<BookList>,
    var profileDefaultLists: ArrayList<BookList>,
    var profileReviews: ArrayList<Activity> = arrayListOf()
)

data class EditProfileMainState(
    var userName: String,
    var userNameError: String? = "",
    var userAlias: String,
    var userAliasError: String? = "",
    var userDescription: String,
    var userDescriptionError: String? = ""

)

class ProfileViewModel(
    private val navController: NavController,
    private val stringResourcesProvider: StringResourcesProvider,
    commonEventHandler: CommonEventHandler
) : ViewModel() {
    var profileInfo by mutableStateOf(
        ProfileMainState(commonEventHandler, obtainUserInfo(), profileDefaultLists(), getUsersProfileLists())
    )

    var profileEditState by mutableStateOf(
        EditProfileMainState(
            userName = profileInfo.user.userName,
            userAlias = profileInfo.user.userAlias,
            userDescription = profileInfo.user.description
        )
    )

    fun onEvent(event: ProfileScreenEvent) {
        when (event) {
            is ProfileScreenEvent.EditButtonClick -> {
                navController.navigate(""/*Navegar a la pantalla de editar perfil*/)
            }

            is ProfileScreenEvent.FollowButtonClick -> {
                navController.navigate("")
            }

            is ProfileScreenEvent.PendingButtonClick -> {
                navController.navigate("")
            }

            is ProfileScreenEvent.UnFollowButtonClick -> {
                navController.navigate("")
            }

            is ProfileScreenEvent.ReviewsButtonClick -> {
                getUserReviews()
                navController.navigate(ProfileNavigationItems.UserReviews.route)
            }

            is ProfileScreenEvent.FollowedButtonClick -> {
                navController.navigate(""/*Navegar a la pantalla de seguidos perfil*/)
            }

            is ProfileScreenEvent.FollowersButtonClick -> {
                navController.navigate(""/*Navegar a la pantalla de seguidores perfil*/)
            }
            is ProfileScreenEvent.ChangeUserName ->{
                profileEditState = profileEditState.copy(userName = event.userName)
            }
            is ProfileScreenEvent.ChangeUserDescription ->{
                profileEditState = profileEditState.copy(userDescription = event.userDescription)
            }
            is ProfileScreenEvent.ChangeUserAlias ->{
                profileEditState = profileEditState.copy(userAlias = event.userAlias)
            }
        }
    }

    private fun obtainUserInfo(): User {
        return User(
            "",
            R.drawable.prueba,
            UserPrivacyPublic(),
            UserFollowStateUnfollow(),
            userName = "Nombre de Usuario2"
        )
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

    private fun getUserReviews() {
        val followedActivity: ArrayList<Activity> = arrayListOf()
        //TODO: Obtener la actividad de las reviews del usuario seleccionado
        val libroTest = Book("Words Of Radiance", "Brandon Sanderson", R.drawable.prueba)
        val userForTesting =
            User("Nombre de Usuario", R.drawable.prueba, UserPrivacyPublic(), UserFollowStateFollowed())
        val reviewForTest = ReviewActivity(
            userForTesting,
            LocalDate.now(),
            libroTest,
            "Muy guapo el libro"
        )

        followedActivity.add(reviewForTest)
        profileInfo = profileInfo.copy(profileReviews = followedActivity)
    }
}