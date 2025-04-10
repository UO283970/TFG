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
        if(list?.numberOfBooks != 0){
            list?.listImage = list.getListOfBooks()[0].coverImage.toString()
        }
    }

    fun removeBookFromUserList(book : Book, bookListClass: BookListClass){
        var list = this.ownLists.find { it.listId == bookListClass.listId }
        list?.getListOfBooks()?.remove(book)
        list?.numberOfBooks--
        if(list?.numberOfBooks == 0){
            list.listImage = ""
        }else{
            list?.listImage = list.getListOfBooks()[0].coverImage.toString()
        }
    }

    fun addBookToDefaultList(book : Book, defaultList: DefaultList){
        var list = this.defaultLists.find { it.listId == defaultList.listId }
        list?.getListOfBooks()?.add(book)
        list?.numberOfBooks++
        if(list?.numberOfBooks != 0){
            list?.listImage = list.getListOfBooks()[0].coverImage.toString()
        }
    }

    fun removeBookFromDefaultList(book : Book, defaultList: DefaultList){
        var list = this.defaultLists.find { it.listId == defaultList.listId }
        list?.getListOfBooks()?.remove(book)
        list?.numberOfBooks--
        if(list?.numberOfBooks == 0 || list?.getListOfBooks()?.isEmpty() == true){
            list.listImage = ""
        }else{
            list?.listImage = list.getListOfBooks()[0].coverImage.toString()
        }
    }


}