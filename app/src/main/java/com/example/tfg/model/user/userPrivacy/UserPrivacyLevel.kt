package com.example.tfg.model.user.userPrivacy

enum class UserPrivacyLevel {
    PRIVATE {
        override fun getMoreInfo(): Boolean {
            return false
        }
    },
    PUBLIC {
        override fun getMoreInfo(): Boolean {
            return true
        }
    };

    abstract fun getMoreInfo(): Boolean
}