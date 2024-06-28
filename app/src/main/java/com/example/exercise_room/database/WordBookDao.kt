package com.example.exercise_room.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Dao (Data access object)
 * Room 使用Dao與資料庫互動
 * - @Query、@Insert、@Delete、@Update
 */
@Dao
interface WordBookDao {

    // 透過Flow 查詢資料庫，及時回覆
    @Query("SELECT * FROM word_book_table")
    fun loadAllWords(): Flow<List<Word>>

    // 複雜查詢也適用Flow，及時回覆
    @Query("SELECT * FROM word_book_table ORDER BY englishWord ASC")
    fun getAllWord(): Flow<List<Word>>

    // 透過suspend 以便Coroutine中執行操作，避免在main Thread
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg word: Word)

    @Update
    suspend fun update(vararg word: Word)

    @Delete
    suspend fun delete(vararg word: Word)

    @Query("DELETE FROM word_book_table")
    suspend fun deleteAll()


}