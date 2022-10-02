package com.example.stayin.repository

import com.example.stayin.data.NoteItem
import kotlinx.coroutines.flow.Flow

interface NotesRepo {

    fun getAll(): Flow<List<NoteItem>>

    suspend fun insertNote(note: NoteItem)

    suspend fun updateNote(note: NoteItem)

    suspend fun deleteNote(note: NoteItem)
}