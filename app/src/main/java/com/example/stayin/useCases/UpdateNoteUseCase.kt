package com.example.stayin.useCases

import com.example.stayin.data.NoteItem
import com.example.stayin.repository.NotesRepo
import com.example.stayin.repository.RepoImplementation

class UpdateNoteUseCase(private val repository: NotesRepo) {

    suspend operator fun invoke(note: NoteItem){
        repository.updateNote(note)
    }
}