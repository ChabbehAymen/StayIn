package com.example.stayin.presentation.ui.mainFragment.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.stayin.useCases.NoteUseCase

class MainFragmentVMFactory(private val noteUseCase: NoteUseCase): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainFragmentViewModel::class.java))
            MainFragmentViewModel(noteUseCase) as T
        else
            throw IllegalAccessException("Unknown class ")
    }
}