package com.example.tfg.model.user

class MainUserState {

    private var mainUser: User? = null

    fun getMainUser(): User?{
        return this.mainUser
    }

    fun setMainUser(user: User){
        this.mainUser = user
    }

}