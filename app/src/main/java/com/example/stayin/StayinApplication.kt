package com.example.stayin

import android.app.Application
import com.example.stayin.data.NoteDatabase

class StayinApplication: Application() {
    val noteDatabase: NoteDatabase by lazy { NoteDatabase.getDatabase(this) }
}