package com.example.tfg.model.booklist

import com.example.tfg.model.book.Book
import com.example.tfg.repository.ListRepository

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

    fun setOwnList(ownLists: ArrayList<BookListClass> ){
        this.ownLists = ownLists
    }

    fun setDefaultList(defaultLists: ArrayList<DefaultList> ){
        this.defaultLists = defaultLists
    }

    fun setDetailsList(bookList: BookList){
        this.detailList = bookList
    }

    fun deleteList(bookListClass: BookListClass){
        this.ownLists.remove(bookListClass)
    }

    fun addBookToUserList(book : Book, bookListClass: BookListClass){
        var list = this.ownLists.find { it.listId == bookListClass.listId }
        list?.getListOfBooks()?.add(book)
        list?.numberOfBooks++
        if(list?.numberOfBooks != 0){
            list?.listImage = list.getListOfBooks()[0].coverImage.toString()
        }
    }

    suspend fun removeBookFromUserList(book : Book, bookListClass: BookListClass, listRepository: ListRepository){
        var list = this.ownLists.find { it.listId == bookListClass.listId }
        list?.getListOfBooks()?.remove(book)
        list?.numberOfBooks--
        if(list?.numberOfBooks == 0){
            list.listImage = ""
        }else{
            list?.listImage = listRepository.getImageForList(list.listId)!!
        }
    }

    fun addBookToDefaultList(book : Book, defaultList: DefaultList){
        var list = this.defaultLists.find { it.listId == defaultList.listId }
        list?.getListOfBooks()?.add(book)
        list?.numberOfBooks++
        book.readingState = list?.listName.toString()
        if(list?.numberOfBooks != 0){
            list?.listImage = list.getListOfBooks()[0].coverImage.toString()
        }
    }

    suspend fun removeBookFromDefaultList(book : Book, defaultList: DefaultList, listRepository: ListRepository){
        var list = this.defaultLists.find { it.listId == defaultList.listId }
        list?.getListOfBooks()?.remove(book)
        list?.numberOfBooks--
        book.readingState = ""
        if(list?.numberOfBooks == 0 || list?.getListOfBooks()?.isEmpty() == true){
            list.listImage = ""
        }else{
            list?.listImage = listRepository.getImageForDefaultList(defaultList.listId)!!
        }
    }


}