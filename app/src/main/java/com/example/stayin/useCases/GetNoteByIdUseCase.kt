package com.example.stayin.useCases

import com.example.stayin.data.NoteItem
import com.example.stayin.repository.NotesRepo
import kotlinx.coroutines.flow.Flow

class GetNoteByIdUseCase(private val repo: NotesRepo) {

    operator fun invoke(id: Int): Flow<NoteItem> {
        return repo.getNoteById(id)
    }
}