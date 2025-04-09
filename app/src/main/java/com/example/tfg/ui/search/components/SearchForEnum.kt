package com.example.tfg.ui.search.components

import com.example.tfg.R

enum class SearchForEnum {
    BOOKS{
        override fun stringResource(): Int {
            return R.string.search_filters_search_for_books
        }

    },
    AUTHOR{
        override fun stringResource(): Int {
            return R.string.search_filters_search_for_author
        }
    },
    LISTS{
        override fun stringResource(): Int {
            return R.string.search_filters_search_for_lists
        }
    };

    abstract fun stringResource(): Int
}