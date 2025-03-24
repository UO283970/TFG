package com.example.tfg.ui.search

import com.example.tfg.R

enum class SubjectsEnum {
    FANTASY{
        override fun getStringResource(): Int {
            return R.string.search_filters_filterFor_fantasy
        }

        override fun getIconResource(): Int {
            return R.drawable.fantasy_icon
        }
    },
    ROMANCE{
        override fun getStringResource(): Int {
            return R.string.search_filters_filterFor_romance
        }

        override fun getIconResource(): Int {
            return R.drawable.love_icon
        }
    },
    MYSTERY{
        override fun getStringResource(): Int {
            return R.string.search_filters_filterFor_mystery
        }

        override fun getIconResource(): Int {
            return R.drawable.mystery_icon
        }
    },
    HORROR{
        override fun getStringResource(): Int {
            return R.string.search_filters_filterFor_horror
        }

        override fun getIconResource(): Int {
            return R.drawable.horror_icon
        }
    },
    SCIENCE_FICTION{
        override fun getStringResource(): Int {
            return R.string.search_filters_filterFor_science_fiction
        }

        override fun getIconResource(): Int {
            return R.drawable.science_fiction_icon
        }
    },
    FILM{
        override fun getStringResource(): Int {
            return R.string.search_filters_filterFor_film
        }

        override fun getIconResource(): Int {
            return R.drawable.film_icon
        }
    },
    ART{
        override fun getStringResource(): Int {
            return R.string.search_filters_filterFor_art
        }

        override fun getIconResource(): Int {
            return R.drawable.art_icon
        }
    },
    MAGIC{
        override fun getStringResource(): Int {
            return R.string.search_filters_filterFor_magic
        }

        override fun getIconResource(): Int {
            return R.drawable.magic_icon
        }
    },
    HUMOR{
        override fun getStringResource(): Int {
            return R.string.search_filters_filterFor_humor
        }

        override fun getIconResource(): Int {
            return R.drawable.humor_icon
        }
    },
    THRILLER{
        override fun getStringResource(): Int {
            return R.string.search_filters_filterFor_thriller
        }

        override fun getIconResource(): Int {
            return R.drawable.thriller_icon
        }
    },
    BIOGRAPHY{
        override fun getStringResource(): Int {
            return R.string.search_filters_filterFor_biography
        }

        override fun getIconResource(): Int {
            return R.drawable.biography_icon
        }
    },
    ADVENTURE{
        override fun getStringResource(): Int {
            return R.string.search_filters_filterFor_adventure
        }

        override fun getIconResource(): Int {
            return R.drawable.adventure_icon
        }
    },
    ;
    abstract fun getStringResource(): Int
    abstract fun getIconResource(): Int
}