package com.example.exercise_room

import androidx.annotation.WorkerThread


class WordRepository(private val wordDao: WordDao) {

    val allWord  = wordDao.getAlphabetizedWords()

    // default Room runs suspend queries off the main thread

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }

}