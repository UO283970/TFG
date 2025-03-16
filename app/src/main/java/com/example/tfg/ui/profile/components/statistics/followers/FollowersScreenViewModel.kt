package com.example.tfg.ui.profile.components.statistics.followers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.tfg.R
import com.example.tfg.model.user.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


data class FollowersScreenMainState(
    var followersList: ArrayList<User>
)

@HiltViewModel
class FollowersScreenViewModel @Inject constructor(): ViewModel() {

    var followersInfo by mutableStateOf(FollowersScreenMainState(getUserFollowers()))

    private fun getUserFollowers(): ArrayList<User> {
        /*TODO: Recuperar los seguidores del usuario que se pasará como parámetro*/
        return arrayListOf(User("Nombre de usuario", profilePicture = R.drawable.prueba))
    }

    fun deleteFollower(user: User){

    }

}