package com.example.tfg.model.booklist

import com.example.tfg.R

enum class DefaultListNames {
    READING{
        override fun getDefaultListName(): Int {
            return R.string.list_default_name_reading
        }

    },
    DROPPED{
        override fun getDefaultListName(): Int {
            return R.string.list_default_name_dropped
        }

    },
    WAITING{
        override fun getDefaultListName(): Int {
            return R.string.list_default_name_waiting
        }

    },
    READ{
        override fun getDefaultListName(): Int {
            return R.string.list_default_name_read
        }

    },
    PLAN_TO_READ{
        override fun getDefaultListName(): Int {
            return R.string.list_default_name_plan_to_read
        }

    },
    NOT_IN_LIST{
        override fun getDefaultListName(): Int {
            return 0
        }

    }

    ;
    abstract fun getDefaultListName(): Int
}