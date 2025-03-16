package com.example.tfg.ui.profile.components.statistics.follows

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.tfg.R
import com.example.tfg.model.user.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


data class FollowsScreenMainState(
    var followsList: ArrayList<User>
)

@HiltViewModel
class FollowsScreenViewModel @Inject constructor(): ViewModel() {

    var followsInfo by mutableStateOf(FollowsScreenMainState(getUserFollows()))

    private fun getUserFollows(): ArrayList<User> {
        /*TODO: Recuperar los seguidos del usuario que se pasará como parámetro*/
        return arrayListOf(User("Nombre de usuario", profilePicture = R.drawable.prueba))
    }

    fun deleteFollow(user: User){

    }

}