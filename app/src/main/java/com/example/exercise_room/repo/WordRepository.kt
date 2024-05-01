package com.example.exercise_room.repo

import androidx.annotation.WorkerThread
import com.example.exercise_room.database.Word
import com.example.exercise_room.database.WordDao


class WordRepository(private val wordDao: WordDao) {

    val allWord  = wordDao.getAll()

    // default Room runs suspend queries off the main thread
    @WorkerThread
    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }

}