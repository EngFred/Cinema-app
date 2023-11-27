package com.omongole.fred.yomovieapp.presentation.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {

    var moviePath by mutableStateOf("")
        private set

    fun putPosterPath(moviePath: String ) {
        this.moviePath = moviePath
    }
}