package com.example.exercise_room.repo

import com.example.exercise_room.database.Word
import kotlinx.coroutines.flow.Flow

interface WordRepository {
    val allWords: Flow<List<Word>>
    suspend fun insert(word: Word)
    suspend fun update(word: Word)
    suspend fun delete(word: Word)
    suspend fun deleteAll()
}