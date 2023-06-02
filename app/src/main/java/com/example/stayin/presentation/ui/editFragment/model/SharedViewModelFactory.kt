package com.example.stayin.presentation.ui.editFragment.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.stayin.useCases.NoteUseCase

class SharedViewModelFactory(private val noteUseCase: NoteUseCase): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SharedViewModel::class.java))
            SharedViewModel(noteUseCase) as T
        else
            throw IllegalAccessException("Unknown class ")
    }
}