package com.example.exercise_room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Word::class], version = 1)
abstract class WordRoomDataBase : RoomDatabase() {
    abstract fun wordBookDao(): WordBookDao

    companion object {
        @Volatile
        private var INSTANCE: WordRoomDataBase? = null
        fun getDatabaseInstance(
            context: Context,
            coroutineScope: CoroutineScope
        ): WordRoomDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context = context.applicationContext,
                    klass = WordRoomDataBase::class.java,
                    name = "word_database"
                ).addCallback(BasicData(coroutineScope)).build()

                INSTANCE = instance
                instance
            }
        }
    }

    // 初次建立資料
    private class BasicData(val coroutineScope: CoroutineScope) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                coroutineScope.launch(Dispatchers.IO) {
                    initDefaultData(database.wordBookDao())
                }
            }
        }

        private suspend fun initDefaultData(dao: WordBookDao) {
            dao.insert(Word(enWord = "1", chWord = "新增"))
        }
    }
}
