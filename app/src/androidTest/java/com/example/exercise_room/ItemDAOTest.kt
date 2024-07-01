package com.example.exercise_room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.example.exercise_room.database.Word
import com.example.exercise_room.database.WordBookDao
import com.example.exercise_room.database.WordRoomDataBase
import kotlinx.coroutines.flow.first
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


    // 驗證資料插入： 插入資料後，使用 DAO 查詢資料，驗證插入的資料是否正確。
    @Test
    fun testInsertItem() = runTest {
        val initialData = Word(id = 1, englishWord = "TEST") // 實際值
        dao.insert(initialData)

        val expected = "TEST" // 預期
        // 驗證剛剛的資料
        val retriever = dao.getWordByEnWord(expected)
        Assert.assertNotNull(retriever)
        Assert.assertEquals(expected, retriever?.englishWord)
    }

    // 驗證資料更新: 更新資料後，使用 DAO 查詢資料，驗證更新後的資料是否正確。
    @Test
    fun testUpdateItem() = runTest {
        val initialData = Word(id = 1, englishWord = "TEST")
        dao.insert(initialData)

        val expected = "UPDATED"
        val updatedData = initialData.copy(englishWord = expected)
        dao.update(updatedData)

        val retrievedData = dao.getWordByEnWord(expected)
        Assert.assertNotNull(retrievedData) // 結果不為null
        Assert.assertEquals(expected, retrievedData?.englishWord)
    }

    // 驗證資料刪除： 刪除資料後，使用 DAO 查詢資料，驗證資料是否已被刪除。
    @Test
    fun testDeleteItem() = runTest {
        val initialData = Word(id = 1, englishWord = "TEST")
        dao.insert(initialData)

        dao.delete(initialData)

        val retrievedData = dao.getWordByEnWord("TEST")
        Assert.assertNull(retrievedData)
    }

    // 驗證資料刪除: 刪除多筆資料，使用 DAO 查詢資料，驗證資料是否已被刪除。
    @Test
    fun testDeleteMultiItem() = runTest {
        dao.insert(
            Word(id = 1, englishWord = "TEST1"),
            Word(id = 2, englishWord = "TEST2"),
            Word(id = 3, englishWord = "TEST3"),
            Word(id = 4, englishWord = "TEST4"),
            Word(id = 5, englishWord = "TEST5")
        )

        dao.delete(
            Word(id = 1, englishWord = "TEST1"),
            Word(id = 2, englishWord = "TEST2")
        )

        val retrievedData1 = dao.getWordByEnWord("TEST1")
        val retrievedData2 = dao.getWordByEnWord("TEST2")
        Assert.assertNull(retrievedData1)
        Assert.assertNull(retrievedData2)
    }


    // 驗證資料刪除: 刪除全部筆資料，使用 DAO 查詢資料，驗證資料是否已被刪除。
    @Test
    fun testAllDeleteDBItem() = runTest {
        dao.insert(Word(englishWord = "TEST1"), Word(englishWord = "TEST2"))

        dao.deleteAll()
        val allData = dao.loadAllWords().first()
        Assert.assertTrue(allData.isEmpty())
    }

    // 驗證 Flow 查詢： 使用 Turbine library 來驗證 Flow emit的資料是否符合預期
    @Test
    fun testFlowQueryDB()  = runTest {
        dao.loadAllWords().test {
            // 驗證空資料庫
            Assert.assertEquals(emptyList<Word>(), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }

        val testData1 = Word(id= 1, englishWord = "TEST1")
        val testData2 = Word(id= 2, englishWord = "TEST2")
        dao.insert(testData1, testData2)

        dao.loadAllWords().test {
            // 驗證 Flow emit包含插入資料的列表，比對是否正確
            Assert.assertEquals(listOf(testData1, testData2), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}