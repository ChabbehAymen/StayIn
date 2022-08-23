package com.example.stayin.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("Select * from noteitem")
    fun getNotes(): Flow<List<NoteItem>>

    @Query("Select tag from noteitem")
    fun getTags(): Flow<List<String>>

    @Insert
    suspend fun insert(noteItem: NoteItem)

    @Update
    suspend fun update(noteItem: NoteItem)

    @Delete
    suspend fun delete(noteItem: NoteItem)
}