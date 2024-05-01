package com.example.exercise_room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class WordRoomDataBase : RoomDatabase() {
    abstract fun wordDao(): WordDao

    companion object {
        @Volatile
        private var INSTANCE: WordRoomDataBase? = null
        fun getDatabaseInstance(context: Context, scope: CoroutineScope): WordRoomDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context = context.applicationContext,
                    klass = WordRoomDataBase::class.java,
                    name = "word_database"
                ).addCallback(WordRoomDataBaseCallBack(scope)).build()

                INSTANCE = instance
                instance
            }
        }
    }

    private class WordRoomDataBaseCallBack(val scope: CoroutineScope) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { dataBase ->
                scope.launch(Dispatchers.IO) {
                    populateDatabase(dataBase.wordDao())
                }
            }
        }

        suspend fun populateDatabase(wordDao: WordDao) {
            wordDao.deleteAll()

            val word = Word("Add")
            wordDao.insert(word)
        }
    }
}