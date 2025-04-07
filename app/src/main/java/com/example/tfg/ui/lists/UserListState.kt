package com.example.tfg.ui.lists

import com.example.tfg.model.booklist.BookList
import com.example.tfg.model.booklist.DefaultList


class UserListState {

    private var ownLists: ArrayList<BookList> = arrayListOf<BookList>()
    private var defaultLists: ArrayList<DefaultList> = arrayListOf<DefaultList>()

    fun getOwnLists(): ArrayList<BookList>{
        return ownLists
    }

    fun getDefaultLists(): ArrayList<DefaultList>{
        return defaultLists
    }

    fun addToOwnList(bookList: BookList){
        ownLists.add(bookList)
    }


    fun addToDefaultList(bookList: DefaultList){
        defaultLists.add(bookList)
    }

    fun setOwnList(ownLists: ArrayList<BookList> ){
        this.ownLists = ownLists
    }

    fun setDefaultList(defaultLists: ArrayList<DefaultList> ){
        this.defaultLists = defaultLists
    }

}