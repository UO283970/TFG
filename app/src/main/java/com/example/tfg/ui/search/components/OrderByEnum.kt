package com.example.tfg.ui.search.components

import com.example.tfg.R
import com.example.tfg.model.book.Book

enum class OrderByEnum {
    DEFAULT{
        override fun order(listToFilter: List<Book>, descending: Boolean): List<Book> {
            return listToFilter
        }

        override fun getStringResource(): Int {
            return R.string.search_filters_order_by_default
        }
    },
    TITTLE{
        override fun order(listToFilter: List<Book>, descending: Boolean): List<Book> {
            return if(descending){
                listToFilter.sortedByDescending { it.tittle }
            }else{
                listToFilter.sortedBy { it.tittle }
            }
        }

        override fun getStringResource(): Int {
            return R.string.search_filters_order_by_tittle
        }
    },
    AUTHOR{
        override fun order(listToFilter: List<Book>, descending: Boolean): List<Book> {
            return if(descending){
                listToFilter.sortedByDescending { it.author }
            }else{
                listToFilter.sortedBy { it.author }
            }
        }

        override fun getStringResource(): Int {
            return R.string.search_filters_order_by_author
        }

    },
    DATE{
        override fun order(listToFilter: List<Book>, descending: Boolean): List<Book> {
            return if(descending){
                listToFilter.sortedByDescending { it.publicationDate }
            }else{
                listToFilter.sortedBy { it.publicationDate }
            }
        }

        override fun getStringResource(): Int {
            return R.string.search_filters_order_by_date
        }

    },
    PAGE_NUMBER{
        override fun order(listToFilter: List<Book>, descending: Boolean): List<Book> {
            return if(descending){
                listToFilter.sortedByDescending { it.pages }
            }else{
                listToFilter.sortedBy { it.pages }
            }
        }

        override fun getStringResource(): Int {
            return R.string.search_filters_order_by_pages
        }

    };

    abstract fun order(listToFilter: List<Book>, descending: Boolean): List<Book>
    abstract fun getStringResource(): Int
}