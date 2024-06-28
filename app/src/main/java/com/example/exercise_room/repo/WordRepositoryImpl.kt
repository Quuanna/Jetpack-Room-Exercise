package com.example.exercise_room.repo

import com.example.exercise_room.database.Word
import com.example.exercise_room.database.WordBookDao
import kotlinx.coroutines.flow.Flow

/**
 * 1. 避免在 Repository 中進行資料操作
 */
class WordRepositoryImpl(private val wordDao: WordBookDao): WordRepository {

    override val allWords: Flow<List<Word>>
        get() = wordDao.loadAllWords()

    override suspend fun insert(word: Word) {
        wordDao.insert(word)
    }

    override suspend fun update(word: Word) {
        wordDao.update(word)
    }

    override suspend fun delete(word: Word) {
        wordDao.delete(word)
    }

    override suspend fun deleteAll() {
        wordDao.deleteAll()
    }
}