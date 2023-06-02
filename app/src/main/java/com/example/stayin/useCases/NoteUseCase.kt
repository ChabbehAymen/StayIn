package com.example.stayin.useCases


data class NoteUseCase (
    val getNotesUseCase: GetNotesUseCase,
    val getNoteByIdUseCase: GetNoteByIdUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val updateNoteUseCase: UpdateNoteUseCase,
    val insertNoteUseCase: InsertNoteUseCase
)
