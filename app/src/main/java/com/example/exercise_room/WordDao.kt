package com.example.exercise_room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Dao (Data access object)
 * Room 使用Dao與資料庫互動
 * - @Query、@Insert、@Delete、@Update
 */
@Dao
interface WordDao {

    @Query("SELECT * FROM word_table ORDER BY word ASC") // 所有word的查詢後回傳列表
    fun getAlphabetizedWords(): Flow<List<Word>> // 保存最新版本的資料快取，當資料發生變化時通知其觀察者，轉liveData

    @Insert(onConflict = OnConflictStrategy.IGNORE) // 忽略新加入的重複字
    suspend fun insert(word: Word)

    @Query("DELETE FROM word_table") // 查詢要刪除的 table
    suspend fun deleteAll()
}