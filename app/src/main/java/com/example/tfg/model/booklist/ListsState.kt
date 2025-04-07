package com.example.tfg.model.booklist

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

}