package com.example.tfg.ui.search.components

import com.example.tfg.R

enum class SearchForEnum {
    BOOKS{
        override fun stringResource(): Int {
            return R.string.search_filters_search_for_title
        }

        override fun searchForToQuery(): String {
            return "intitle:"
        }

    },
    AUTHOR{
        override fun stringResource(): Int {
            return R.string.search_filters_search_for_author
        }

        override fun searchForToQuery(): String {
            return "inauthor:"
        }
    },
    ISBN{
        override fun stringResource(): Int {
            return R.string.search_filters_search_for_isbn
        }

        override fun searchForToQuery(): String {
            return "isbn:"
        }
    };

    abstract fun stringResource(): Int
    abstract fun searchForToQuery(): String
}