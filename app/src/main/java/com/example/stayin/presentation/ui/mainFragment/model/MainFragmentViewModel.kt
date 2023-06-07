package com.example.stayin.presentation.ui.mainFragment.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stayin.data.NoteItem
import com.example.stayin.useCases.NoteUseCase
import kotlinx.coroutines.launch

class MainFragmentViewModel(private val noteUseCase: NoteUseCase): ViewModel() {

    private val noteList = mutableListOf<NoteItem>()

    fun getAllNotes(): List<NoteItem>{
        viewModelScope.launch {
            collectGetNotes()
        }
        return noteList
    }

    private suspend fun collectGetNotes() = noteUseCase.getNotesUseCase.invoke().collect{ noteList.addAll(it) }

    fun getNoteItemById(id: Int): NoteItem {
        return noteList.find { it.id == id }!!
    }

    fun deleteNoteItem(noteItem: NoteItem){
        viewModelScope.launch {
            noteUseCase.deleteNoteUseCase.invoke(noteItem)
        }
    }
}


