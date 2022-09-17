package com.example.stayin.ui.models

import android.util.Log
import androidx.lifecycle.*
import com.example.stayin.data.NoteDao
import com.example.stayin.data.NoteItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

class MainViewModel(private val noteDao: NoteDao): ViewModel() {

    fun getNotes(): Flow<List<NoteItem>>{
        Log.i("MainFragmentViewModel", "${noteDao.getNotes()}")
        return noteDao.getNotes()
    }

}