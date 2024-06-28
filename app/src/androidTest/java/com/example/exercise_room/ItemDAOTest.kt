package com.example.exercise_room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.exercise_room.database.Word
import com.example.exercise_room.database.WordBookDao
import com.example.exercise_room.database.WordRoomDataBase
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ItemDAOTest {

    private lateinit var dao: WordBookDao
    private lateinit var dataBase: WordRoomDataBase

    // 建立測試環境，在記憶體內建立資料庫，避免與影響到實際資料庫
    @Before
    fun createDB() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        dataBase = Room.inMemoryDatabaseBuilder(context, WordRoomDataBase::class.java).build()
        dao = dataBase.wordBookDao()
    }

    @After
    fun closeDB() {
        dataBase.close()
    }

    /**
     * DAO Test
     */

    // 驗證資料插入： 插入資料後，使用 DAO 查詢資料，驗證插入的資料是否正確。
    @Test
    fun testInsertItem() = runTest {
        val data = Word(englishWord = "TEST") // 實際值
        dao.insert(data)

        val expected = "TEST" // 預期
        // 驗證剛剛的資料
        val retriever = dao.getWordByEnWord(expected)
        Assert.assertNotNull(retriever) // 結果不為null
        Assert.assertEquals(expected, retriever?.englishWord) // 是否查到跟預期一樣
    }

    // TODO  驗證資料更新: 更新資料後，使用 DAO 查詢資料，驗證更新後的資料是否正確。

    // TODO　驗證資料刪除： 刪除資料後，使用 DAO 查詢資料，驗證資料是否已被刪除。

    // TODO　驗證 Flow 查詢： 使用 turbine 函式庫來驗證 Flow 發出的資料是否符合預期
}