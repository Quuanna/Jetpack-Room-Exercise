package com.example.exercise_room

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

    private class WordRoomDataBaseCallBack(val scope: CoroutineScope) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            // 如果想在應用程式重新啟動時保留數據，請註解掉以下行
            INSTANCE?.let { dataBase ->
                scope.launch(Dispatchers.IO) {
                    populateDatabase(dataBase.wordDao())
                }
            }
        }

        /**
         * Populate the database in a new coroutine.
         * If you want to start with more words, just add them.
         */
        suspend fun populateDatabase(wordDao: WordDao) {
            wordDao.deleteAll() // 如果不需清空在註解

            var word = Word("Hello")
            wordDao.insert(word)
            word = Word("World!")
            wordDao.insert(word)
        }
    }
}