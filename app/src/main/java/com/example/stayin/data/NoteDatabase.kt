package com.example.stayin.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NoteItem::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {

        const val DATABASE_NAME = "note_database"
//        @Volatile
//        private var INSTANCE: NoteDatabase? = null
//
//
//        fun getDatabase(context: Context): NoteDatabase{
//            return INSTANCE ?: synchronized(this){
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    NoteDatabase::class.java,
//                    "note_database"
//                )
//                    .fallbackToDestructiveMigration()
//                    .build()
//                INSTANCE = instance
//                instance
//            }
//        }
    }
}