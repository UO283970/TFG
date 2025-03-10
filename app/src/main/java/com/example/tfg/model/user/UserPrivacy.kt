package com.example.tfg.model.user

import com.example.tfg.model.user.userPrivacy.UserPrivacyLevel

interface UserPrivacy {
    fun getShowMainInfo(): Boolean
    fun getPrivacyLevel(): UserPrivacyLevel
}