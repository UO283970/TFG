package com.example.tfg.model.user

interface UserFollowState {
    fun getButtonAction(): User.UserButtonConfig
    fun getCanShowMoreInfo(): Boolean
}