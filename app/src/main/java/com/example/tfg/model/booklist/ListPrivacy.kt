package com.example.tfg.model.booklist

import com.example.tfg.R
import com.example.tfg.ui.common.StringResourcesProvider

enum class ListPrivacy{
    PUBLIC{
        override fun getListPrivacyString(stringResourcesProvider: StringResourcesProvider):String {
            return stringResourcesProvider.getString(R.string.list_privacy_public)
        }
    },
    PRIVATE{
        override fun getListPrivacyString(stringResourcesProvider: StringResourcesProvider):String {
            return stringResourcesProvider.getString(R.string.list_privacy_private)
        }
    },
    ONLY_FOLLOWERS{
        override fun getListPrivacyString(stringResourcesProvider: StringResourcesProvider):String {
            return stringResourcesProvider.getString(R.string.list_privacy_only_friends)
        }
    };
    abstract fun getListPrivacyString(stringResourcesProvider: StringResourcesProvider): String
}