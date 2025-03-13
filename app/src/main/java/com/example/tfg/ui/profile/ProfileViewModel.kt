package com.example.tfg.ui.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.tfg.R
import com.example.tfg.model.AppConstants
import com.example.tfg.model.Book
import com.example.tfg.model.booklist.BookList
import com.example.tfg.model.user.Activity
import com.example.tfg.model.user.User
import com.example.tfg.model.user.userActivities.ReviewActivity
import com.example.tfg.model.user.userFollowStates.UserFollowStateFollowed
import com.example.tfg.model.user.userFollowStates.UserFollowStateUnfollow
import com.example.tfg.model.user.userPrivacy.UserPrivacyLevel
import com.example.tfg.model.user.userPrivacy.UserPrivacyPublic
import com.example.tfg.ui.common.CommonEventHandler
import com.example.tfg.ui.common.StringResourcesProvider
import com.example.tfg.ui.common.navHost.ProfileNavigationItems
import java.time.LocalDate

data class ProfileMainState(
    val commonEventHandler: CommonEventHandler,
    val user: User,
    var profileDefaultLists: ArrayList<BookList>,
    var profileBookLists: ArrayList<BookList>,
    var profileReviews: ArrayList<Activity> = arrayListOf()
)

data class EditProfileMainState(
    var userProfilePicture: Int,
    var userName: String,
    var userNameError: String? = null,
    var userAlias: String,
    var userDescription: String,
    var switchState:Boolean

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
            userProfilePicture = profileInfo.user.profilePicture,
            userName = profileInfo.user.userName,
            userAlias = profileInfo.user.userAlias,
            userDescription = profileInfo.user.description,
            switchState = profileInfo.user.privacy.getPrivacyLevel() == UserPrivacyLevel.PRIVATE
        )
    )

    private fun editButtonClick() {
        navController.navigate(ProfileNavigationItems.EditProfile.route)
    }

    fun reviewsButtonClick(){
        navController.navigate(""/*Navegar a la pantalla de seguidores perfil*/)
    }
    fun followedButtonClick(){
        navController.navigate(""/*Navegar a la pantalla de seguidores perfil*/)
    }
    fun followersButtonClick(){
        navController.navigate(""/*Navegar a la pantalla de seguidores perfil*/)
    }

    fun changeUserName(userName: String) {
        profileEditState = profileEditState.copy(userName = userName)
    }

    fun changeUserDescription(userDescription: String) {
        if (profileEditState.userDescription.length <= AppConstants.DESC_MAX_CHARACTERS) {
            profileEditState = profileEditState.copy(
                userDescription = userDescription
            )
        }
    }

    fun changeUserAlias(userAlias: String) {
        profileEditState = profileEditState.copy(userAlias = userAlias)
    }

    fun changeSwitch() {
        profileEditState = profileEditState.copy(switchState = !profileEditState.switchState)
    }

    fun saveButtonOnClick(){
        /*TODO: Guardar los cambios del usuario en la base de datos*/
        if(userNameCheck()){
            navController.popBackStack()
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

    private fun userNameCheck(): Boolean {
        if (profileEditState.userName.isBlank()) {
            profileEditState = profileEditState.copy(userNameError = stringResourcesProvider.getString(R.string.error_userName_empty))
            return false
        }

        /*TODO: Mirar que no exista ya el nombre de usuario*/

        profileEditState = profileEditState.copy(userNameError = null)
        return true
    }
}
