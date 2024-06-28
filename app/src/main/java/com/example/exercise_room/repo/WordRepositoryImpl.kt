package com.example.exercise_room.repo

import com.example.exercise_room.database.Word
import com.example.exercise_room.database.WordBookDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

/**
 * 1. 避免在 Repository 中進行資料操作
 */
class WordRepositoryImpl(private val wordDao: WordBookDao) : WordRepository {

    override fun allWords(): Flow<Result<List<Word>>> {
        return wordDao.loadAllWords()
            .map { Result.success(it) }
            .catch { e -> emit(Result.failure(e)) }
    }

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