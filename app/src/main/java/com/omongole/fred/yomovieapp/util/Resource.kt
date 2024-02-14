package com.omongole.fred.yomovieapp.util

sealed class Resource<out R> {
    data class Success<out R>( val result: R) : Resource<R>()
    data class Error( val message: String) : Resource<Nothing>()
    data object Loading : Resource<Nothing>()
}
