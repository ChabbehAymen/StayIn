package com.example.stayin.di

import androidx.room.Room
import com.example.stayin.data.NoteDatabase
import com.example.stayin.presentation.Application
import com.example.stayin.repository.NotesRepo
import com.example.stayin.repository.RepoImplementation
import com.example.stayin.useCases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NotesRepo {
        return RepoImplementation(db.noteDao())
    }

    @Provides
    @Singleton
    fun provideNoteUseCase(repo: NotesRepo): NoteUseCase {
        return NoteUseCase(
            getNotesUseCase = GetNotesUseCase(repo),
            deleteNoteUseCase = DeleteNoteUseCase(repo),
            updateNoteUseCase = UpdateNoteUseCase(repo),
            insertNoteUseCase = InsertNoteUseCase(repo)
        )
    }
}