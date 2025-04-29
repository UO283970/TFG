package com.example.tfg.repository.mappers

import com.example.tfg.model.book.Book
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
                    list.listImage,
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
            books = this.listOfBooks.toAppListBookFromDefault(stringResourcesProvider),
            listDescription = this.description
        )
    }

    return null
}


fun List<ListOfBook>.toAppListBookFromDefault(stringResourcesProvider: StringResourcesProvider): ArrayList<Book> {
    var listOfBooksApp = arrayListOf<Book>()

    for(book in this){
        listOfBooksApp.add(book.toAppBook(stringResourcesProvider))
    }

    return listOfBooksApp
}


fun List<GetBasicListInfoList>?.toAppLists(userId: String): List<BookListClass>? {
    var appDefaultBookListClasses = arrayListOf<BookListClass>()

    if (this != null) {
        for (list in this) {
            appDefaultBookListClasses.add(
                BookListClass(
                    list.listId,
                    list.listName,
                    list.listImage,
                    numberOfBooks = list.numberOfBooks,
                    userId = userId
                )
            )
        }
    }

    return appDefaultBookListClasses
}

fun GetAllListInfo?.toBookList(stringResourcesProvider: StringResourcesProvider): BookListClass? {
    if(this != null){
        return BookListClass(
            this.listId,
            this.listName,
            books = this.listOfBooks.toAppListBook(stringResourcesProvider),
            listDescription = this.description
        )
    }

    return null
}

private fun List<GetAllListInfoQuery.ListOfBook>.toAppListBook(stringResourcesProvider: StringResourcesProvider): ArrayList<Book> {
    var listOfBooksApp = arrayListOf<Book>()

    for(book in this){
        listOfBooksApp.add(book.toAppBook(stringResourcesProvider))
    }

    return listOfBooksApp
}