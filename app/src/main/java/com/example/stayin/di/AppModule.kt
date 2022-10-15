package com.example.stayin.di

import android.content.Context
import androidx.room.Insert
import androidx.room.Room
import com.example.stayin.data.NoteDatabase
import com.example.stayin.data.NoteItem
import com.example.stayin.presentation.Application
import com.example.stayin.repository.NotesRepo
import com.example.stayin.repository.RepoImplementation
import com.example.stayin.useCases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideNoteDatabase(@ApplicationContext appContext: Context): NoteDatabase {
        return Room.databaseBuilder(
            appContext,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideNoteRepository(database: NoteDatabase): NotesRepo {
        return RepoImplementation(database.noteDao())
    }

    @Provides
    fun provideNoteUseCase(noteRepo: NotesRepo): NoteUseCase {
        return NoteUseCase(
            getNotesUseCase = GetNotesUseCase(noteRepo),
            deleteNoteUseCase = DeleteNoteUseCase(noteRepo),
            updateNoteUseCase = UpdateNoteUseCase(noteRepo),
            insertNoteUseCase = InsertNoteUseCase(noteRepo)
        )
    }
}