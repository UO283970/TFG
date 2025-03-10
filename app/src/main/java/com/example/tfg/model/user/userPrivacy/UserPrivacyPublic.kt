package com.example.tfg.model.user.userPrivacy

import com.example.tfg.model.user.UserPrivacy

class UserPrivacyPublic: UserPrivacy {
    override fun getShowMainInfo(): Boolean {
        return true
    }
    override fun getPrivacyLevel(): UserPrivacyLevel {
        return UserPrivacyLevel.PUBLIC
    }
}