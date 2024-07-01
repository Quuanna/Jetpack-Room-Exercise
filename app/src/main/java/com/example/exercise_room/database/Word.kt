package com.example.exercise_room.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * 目前只搜尋特定欄位，提高索引效率
 */
@Entity(tableName = "word_book_table", indices = [Index(value = ["englishWord"])])
data class Word(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Room 會自動為自動產生的主鍵處理空值
    @ColumnInfo val englishWord: String= "",
    @ColumnInfo val phoneticSymbol: String= "",
    @ColumnInfo val chineseWord: String= "",
    @ColumnInfo val wordType: String= "",
    @ColumnInfo val describe: String= "",
    @ColumnInfo val example: String= ""
):Serializable
