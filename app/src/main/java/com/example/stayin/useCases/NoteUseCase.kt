package com.example.stayin.useCases


data class NoteUseCase (
    val getNotesUseCase: GetNotesUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val updateNoteUseCase: UpdateNoteUseCase,
    val insertNoteUseCase: InsertNoteUseCase
)
