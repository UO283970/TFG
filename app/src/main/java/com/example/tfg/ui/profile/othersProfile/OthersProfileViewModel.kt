package com.example.tfg.ui.profile.othersProfile

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
import com.example.tfg.model.user.userActivities.ReviewActivity
import com.example.tfg.model.user.userFollowStates.UserFollowStateEnum
import com.example.tfg.model.user.userPrivacy.UserPrivacyLevel
import com.example.tfg.ui.common.StringResourcesProvider
import com.google.gson.Gson
import com.google.gson.GsonBuilder
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
class OthersProfileViewModel @Inject constructor(
    private val stringResourcesProvider: StringResourcesProvider,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val gson: Gson = GsonBuilder().create()
    val userJson = savedStateHandle.get<String?>("user")
    private val user = gson.fromJson(userJson, User::class.java)

    var profileInfo by mutableStateOf(
        ProfileMainState(obtainUserInfo(user), profileDefaultLists(user), getUsersProfileLists(user))
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

    fun getUserReviews() {
        val followedActivity: ArrayList<Activity> = arrayListOf()
        //TODO: Obtener la actividad de las reviews del usuario seleccionado
        val libroTest = Book("Words Of Radiance", "Brandon Sanderson", R.drawable.prueba)
        val userForTesting =
            User("Nombre de Usuario", R.drawable.prueba, UserPrivacyLevel.PUBLIC, UserFollowStateEnum.REQUESTED)
        val reviewForTest = ReviewActivity(
            userForTesting,
            LocalDate.now(),
            libroTest,
            "Muy guapo el libro"
        )

        followedActivity.add(reviewForTest)
        profileInfo = profileInfo.copy(profileReviews = followedActivity)
    }

    fun checkConnectedUser(): Boolean {
        return user?.followState == UserFollowStateEnum.OWN
    }
}
