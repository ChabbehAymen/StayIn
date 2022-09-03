package com.example.stayin.ui.models

import androidx.lifecycle.*
import com.example.stayin.data.NoteDao
import com.example.stayin.data.NoteItem
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

class MainViewModel(private val noteDao: NoteDao): ViewModel() {

    fun getNotes(): LiveData<List<NoteItem>>{
        return noteDao.getNotes().asLiveData()
    }

    fun getNote(id: Int): NoteItem{
        return noteDao.getNote(id).asLiveData().value!!
    }

}