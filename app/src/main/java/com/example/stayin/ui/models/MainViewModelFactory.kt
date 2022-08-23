package com.example.stayin.ui.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.stayin.data.NoteDao
import java.lang.IllegalArgumentException

class MainViewModelFactory(private val noteDao: NoteDao): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java))
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(noteDao) as T
        throw IllegalArgumentException("Unknown Class")
    }
}