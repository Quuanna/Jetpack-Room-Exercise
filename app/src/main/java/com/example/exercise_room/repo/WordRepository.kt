package com.example.exercise_room.repo

import com.example.exercise_room.database.Word
import com.example.exercise_room.database.WordDao


class WordRepository(private val wordDao: WordDao) {

    val allWord  = wordDao.getAll() // 排序
//    val allWord  = wordDao.loadAllWords() // 不排序

    // default Room runs suspend queries off the main thread
    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }

    suspend fun edit(old: Word , word: Word) { // 更新資料需要先撈取要改變的資料
        // TODO
        wordDao.update(word)
    }
    suspend fun delete(word: Word) {  // 刪除資料需要先撈取要刪除的資料
        wordDao.delete(word)
    }

    suspend fun deleteAll() {  // 刪除資料需要先撈取要刪除的資料
        wordDao.deleteAll()
    }


}