package com.example.stayin.repository

import com.example.stayin.data.NoteDao
import com.example.stayin.data.NoteItem
import kotlinx.coroutines.flow.Flow

class RepoImplementation(private val noteDao: NoteDao): NotesRepo {
    override fun getAll(): Flow<List<NoteItem>> {
        return noteDao.getAll()
    }

    override fun getNoteById(id: Int): Flow<NoteItem> {
        return noteDao.getNote(id)
    }

    override suspend fun insertNote(note: NoteItem) {
        noteDao.insert(note)
    }

    override suspend fun updateNote(note: NoteItem) {
        noteDao.update(note)
    }

    override suspend fun deleteNote(note: NoteItem) {
        noteDao.delete(note)
    }
}