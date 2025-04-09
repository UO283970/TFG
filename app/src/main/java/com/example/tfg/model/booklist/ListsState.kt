package com.example.tfg.model.booklist

import com.example.tfg.model.Book

class ListsState {

    private var ownLists: ArrayList<BookListClass> = arrayListOf<BookListClass>()
    private var defaultLists: ArrayList<DefaultList> = arrayListOf<DefaultList>()
    private lateinit var detailList: BookList

    fun getOwnLists(): ArrayList<BookListClass>{
        return ownLists
    }

    fun getDefaultLists(): ArrayList<DefaultList>{
        return defaultLists
    }

    fun getDetailList(): BookList{
        return detailList
    }

    fun addToOwnList(bookListClass: BookListClass){
        ownLists.add(bookListClass)
    }


    fun addToDefaultList(bookList: DefaultList){
        defaultLists.add(bookList)
    }

    fun setOwnList(ownLists: ArrayList<BookListClass> ){
        this.ownLists = ownLists
    }

    fun setDefaultList(defaultLists: ArrayList<DefaultList> ){
        this.defaultLists = defaultLists
    }

    fun setDetailsList(bookList: BookList){
        this.detailList = bookList
    }

    fun addBookToUserList(book : Book, bookListClass: BookListClass){
        var list = this.ownLists.find { it.listId == bookListClass.listId }
        list?.getListOfBooks()?.add(book)
        list?.numberOfBooks++
    }

    fun removeBookFromUserList(book : Book, bookListClass: BookListClass){
        var list = this.ownLists.find { it.listId == bookListClass.listId }
        list?.getListOfBooks()?.remove(book)
        list?.numberOfBooks--
    }

    fun addBookToDefaultList(book : Book, defaultList: DefaultList){
        var list = this.defaultLists.find { it.listId == defaultList.listId }
        list?.getListOfBooks()?.add(book)
        list?.numberOfBooks++
    }

    fun removeBookFromDefaultList(book : Book, defaultList: DefaultList){
        var list = this.defaultLists.find { it.listId == defaultList.listId }
        list?.getListOfBooks()?.remove(book)
        list?.numberOfBooks--
    }


}