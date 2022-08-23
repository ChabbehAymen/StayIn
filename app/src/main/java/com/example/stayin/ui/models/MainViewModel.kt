package com.example.stayin.ui.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.stayin.data.NoteDao
import com.example.stayin.data.NoteItem

class MainViewModel(private val noteDao: NoteDao): ViewModel() {

    private var _notes: LiveData<List<NoteItem>> = noteDao.getNotes().asLiveData()
    val notes get() = _notes.value

    fun getNotes(){

    }

    fun saveNote(note: NoteItem){
//        noteDao.insert(note)
    }

}