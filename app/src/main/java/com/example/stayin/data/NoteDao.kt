package com.example.stayin.data

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("Select * from noteitem")
    fun getNotes(): Flow<List<NoteItem>>

    @Query("Select tag from noteitem")
    fun getTags(): Flow<List<String>>

}