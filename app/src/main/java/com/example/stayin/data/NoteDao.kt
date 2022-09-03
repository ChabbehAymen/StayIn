package com.example.stayin.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("Select * from note_database")
    fun getNotes(): Flow<List<NoteItem>>

    @Query("Select * from note_database where id=:id")
    fun getNote(id: Int):Flow<NoteItem>

    @Query("Select tag from note_database")
    fun getTags(): Flow<List<String>>

    @Insert
    suspend fun insert(noteItem: NoteItem)

    @Update
    suspend fun update(noteItem: NoteItem)

    @Delete
    suspend fun delete(noteItem: NoteItem)
}