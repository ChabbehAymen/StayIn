package com.example.stayin.useCases

import com.example.stayin.data.NoteItem
import com.example.stayin.repository.NotesRepo
import com.example.stayin.repository.RepoImplementation
import kotlinx.coroutines.flow.Flow

class GetNotesUseCase(private val repository: NotesRepo) {

    operator fun invoke(): Flow<List<NoteItem>>{
        return repository.getAll()
    }
}