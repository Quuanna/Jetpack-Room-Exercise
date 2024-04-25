package com.example.exercise_room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class WordRoomDataBase: RoomDatabase() {
    abstract fun wordDao(): WordDao

    companion object {
        @Volatile
        private var INSTANCE: WordRoomDataBase? = null
        fun getDatabaseInstance(context: Context): WordRoomDataBase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context = context.applicationContext,
                    klass = WordRoomDataBase::class.java,
                    name = "word_database").build()
                INSTANCE = instance
                instance
            }
        }
    }
}