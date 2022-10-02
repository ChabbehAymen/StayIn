package com.example.stayin.useCases

import javax.inject.Inject

data class NoteUseCase @Inject constructor(
    val getNotesUseCase: GetNotesUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val updateNoteUseCase: UpdateNoteUseCase,
    val insertNoteUseCase: InsertNoteUseCase
)
