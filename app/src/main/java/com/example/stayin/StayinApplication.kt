package com.example.stayin

import android.app.Application
import android.content.Context
import com.example.stayin.data.NoteDatabase
// all ways remember to add the name attr in the application tag in the manifests tag.
//"when ever" you add a application file like this
class StayinApplication: Application() {
    val noteDatabase: NoteDatabase by lazy { NoteDatabase.getDatabase(this) }
}