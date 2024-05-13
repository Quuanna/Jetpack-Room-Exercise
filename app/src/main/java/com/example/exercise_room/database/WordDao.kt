package com.example.exercise_room.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Dao (Data access object)
 * Room 使用Dao與資料庫互動
 * - @Query、@Insert、@Delete、@Update
 */
@Dao
interface WordDao {

    @Query("SELECT * FROM word_table ORDER BY word ASC")
    fun getAll(): Flow<List<Word>>

    @Query("SELECT * FROM word_table")
    fun loadAllWords(): Flow<List<Word>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg word: Word)

    @Update
    suspend fun update(vararg word: Word)
    @Delete
    suspend fun delete(vararg word: Word)

    @Query("DELETE FROM word_table")
    suspend fun deleteAll()

}