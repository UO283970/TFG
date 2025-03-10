package com.example.tfg.model.user.userPrivacy

import com.example.tfg.model.user.UserPrivacy

class UserPrivacyPrivate: UserPrivacy {
    override fun getShowMainInfo(): Boolean {
        return false
    }

    override fun getPrivacyLevel(): UserPrivacyLevel {
        return UserPrivacyLevel.PRIVATE
    }
}