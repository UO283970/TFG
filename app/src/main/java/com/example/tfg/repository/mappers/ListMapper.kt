package com.example.tfg.repository.mappers

import com.example.tfg.R
import com.example.tfg.model.booklist.BookList
import com.example.tfg.model.booklist.DefaultList
import com.example.tfg.model.booklist.DefaultListNames
import com.graphQL.GetBasicListInfoListQuery.GetBasicListInfoList
import com.graphQL.GetUserDefaultListsListQuery.GetUserDefaultListsList

fun List<GetUserDefaultListsList>?.toDefaultAppLists(): List<DefaultList>? {
    var appDefaultBookLists = arrayListOf<DefaultList>()

    if (this != null) {
        for (list in this) {
            appDefaultBookLists.add(
                DefaultList(
                    list.listId,
                    DefaultListNames.valueOf(list.listName).getDefaultListName(),
                    R.drawable.prueba,
                    numberOfBooks = list.numberOfBooks
                )
            )
        }
    }

    return appDefaultBookLists
}

fun List<GetBasicListInfoList>?.toAppLists(): List<BookList>? {
    var appDefaultBookLists = arrayListOf<BookList>()

    if (this != null) {
        for (list in this) {
            appDefaultBookLists.add(
                BookList(
                    list.listId,
                    list.listName,
                    R.drawable.prueba,
                    numberOfBooks = list.numberOfBooks
                )
            )
        }
    }

    return appDefaultBookLists
}