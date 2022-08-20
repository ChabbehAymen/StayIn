package com.example.stayin.data

import android.widget.ImageView
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteItem (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo
    val title: String,
    @ColumnInfo
    val text: String,
    @ColumnInfo
    val image: ImageView,
    @ColumnInfo
    val tag: String,
    @ColumnInfo
    val date: String
                     )