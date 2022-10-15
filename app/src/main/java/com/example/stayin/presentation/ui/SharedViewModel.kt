package com.example.stayin.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stayin.data.NoteItem
import com.example.stayin.presentation.utils.ConstantValues
import com.example.stayin.useCases.NoteUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SharedViewModel : ViewModel() {

    @Inject lateinit var noteUseCase: NoteUseCase
    private val noteId = 0
    private var noteTitle = ""
    private var noteText = ""
    private lateinit var noteDateAndTime: String
    private var noteImage = ConstantValues.nullString
    private var noteTag = "NOTE"
    private var noteColor = "WHITE"
    private var _isOnEditMode = true
    private var notesList = emptyList<NoteItem>()
    val isOnEditMode get() = _isOnEditMode
    private lateinit var _editingNote: NoteItem
    val editingNote get() = _editingNote

    fun getNotes(): List<NoteItem> {
        try {
            viewModelScope.launch {
                noteUseCase.getNotesUseCase.invoke().collect {
                    notesList = it
                }
            }
        } catch (exception: Exception) {
            println("getListViewModelException: $exception")
        }
        return notesList
    }

    private fun collectedNote(): NoteItem {
        return NoteItem(
            id = noteId,
            title = noteTitle,
            text = noteText,
            image = noteImage,
            tag = noteTag,
            date = noteDateAndTime,
            color = noteColor
        )
    }

    fun insertNewNote() {
        viewModelScope.launch(Dispatchers.IO) {
            noteUseCase.insertNoteUseCase.invoke(collectedNote())
        }
    }

    fun updateNote() {
        viewModelScope.launch {
            noteUseCase.updateNoteUseCase.invoke(collectedNote())
        }
    }

    fun getNoteById(id: Int): NoteItem {
        val note = notesList.find { it.id == id }
        _editingNote = note!!
        return note
    }


    fun getNoteTitle(title: String) {
        noteTitle = title
    }

    fun getNoteText(text: String) {
        noteText = text
    }

    fun getNoteDateAndTime(dateAndTime: String) {
        noteDateAndTime = dateAndTime
    }

    fun getNoteImageUri(imageUri: String) {
        noteImage = imageUri
    }

    fun getNoteTag(tag: String) {
        noteTag = tag
    }

    fun getNoteColor(color: String) {
        noteColor = color
    }

    fun getIsOnEditMode(editMode: Boolean) {
        _isOnEditMode = editMode
    }

}