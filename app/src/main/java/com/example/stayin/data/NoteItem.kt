package com.example.stayin.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_database")
data class NoteItem (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo
    val title: String,
    @ColumnInfo
    val text: String,
    @ColumnInfo
    val image: String,
    @ColumnInfo
    val tag: String,
    @ColumnInfo
    val date: String,
    @ColumnInfo
    val color: String
                     )