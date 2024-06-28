package com.example.exercise_room.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_book_table")
data class Word(
    @PrimaryKey(autoGenerate = true) val id: Int? = 0,
    @ColumnInfo var enWord: String? = null,
    @ColumnInfo val phoneticSymbol: String? = null,
    @ColumnInfo val chWord: String? = null,
    @ColumnInfo val type: String? = null,
    @ColumnInfo val describe: String? = null,
    @ColumnInfo val example: String? = null
)
