package com.example.tfg.repository.mappers

import com.example.tfg.R
import com.example.tfg.model.Book
import com.example.tfg.model.booklist.BookListClass
import com.example.tfg.model.booklist.DefaultList
import com.example.tfg.model.booklist.DefaultListNames
import com.example.tfg.ui.common.StringResourcesProvider
import com.graphQL.GetAllListInfoQuery
import com.graphQL.GetAllListInfoQuery.GetAllListInfo
import com.graphQL.GetBasicListInfoListQuery.GetBasicListInfoList
import com.graphQL.GetUserDefaultListQuery.GetUserDefaultList
import com.graphQL.GetUserDefaultListQuery.ListOfBook
import com.graphQL.GetUserDefaultListsListQuery.GetUserDefaultListsList

fun List<GetUserDefaultListsList>?.toDefaultAppLists(userId: String, stringResourcesProvider: StringResourcesProvider): List<DefaultList>? {
    var appDefaultBookLists = arrayListOf<DefaultList>()

    if (this != null) {
        for (list in this) {
            appDefaultBookLists.add(
                DefaultList(
                    list.listId,
                    stringResourcesProvider.getString(DefaultListNames.valueOf(list.listName).getDefaultListName()),
                    R.drawable.prueba,
                    numberOfBooks = list.numberOfBooks,
                    userId = userId
                )
            )
        }
    }

    return appDefaultBookLists
}

fun GetUserDefaultList?.toDefaultList(stringResourcesProvider: StringResourcesProvider): DefaultList? {
    if(this != null){
        return DefaultList(
            this.listId,
            stringResourcesProvider.getString(DefaultListNames.valueOf(this.listName).getDefaultListName()),
            books = this.listOfBooks.toAppListBookFromDefault(),
            listDescription = this.description
        )
    }

    return null
}


fun List<ListOfBook>.toAppListBookFromDefault(): ArrayList<Book> {
    var listOfBooksApp = arrayListOf<Book>()

    for(book in this){
        listOfBooksApp.add(book.toAppBook())
    }

    return listOfBooksApp
}


fun List<GetBasicListInfoList>?.toAppLists(): List<BookListClass>? {
    var appDefaultBookListClasses = arrayListOf<BookListClass>()

    if (this != null) {
        for (list in this) {
            appDefaultBookListClasses.add(
                BookListClass(
                    list.listId,
                    list.listName,
                    R.drawable.prueba,
                    numberOfBooks = list.numberOfBooks
                )
            )
        }
    }

    return appDefaultBookListClasses
}

fun GetAllListInfo?.toBookList(): BookListClass? {
    if(this != null){
        return BookListClass(
            this.listId,
            this.listName,
            books = this.listOfBooks.toAppListBook(),
            listDescription = this.description
        )
    }

    return null
}

private fun List<GetAllListInfoQuery.ListOfBook>.toAppListBook(): ArrayList<Book> {
    var listOfBooksApp = arrayListOf<Book>()

    for(book in this){
        listOfBooksApp.add(book.toAppBook())
    }

    return listOfBooksApp
}