![螢幕擷取畫面 2024-06-28 102312](https://github.com/Quuanna/Jetpack-Room-Exercise/assets/36694083/651af0ae-f3a0-49e9-8687-8596a020bbb9)

## Room是關聯式資料庫
> Room 是[關聯式資料庫](https://zh.wikipedia.org/zh-tw/%E5%85%B3%E7%B3%BB%E6%95%B0%E6%8D%AE%E5%BA%93) **RDBMS**(Relational Database Management System)的資料庫，建立在[關聯式模型](https://zh.wikipedia.org/wiki/%E5%85%B3%E7%B3%BB%E6%A8%A1%E5%9E%8B)，由資料結構、操作集合、完整性約束三部分組合，使用標準資料查詢語言[SQL](https://zh.wikipedia.org/wiki/SQL)就是一種基於關聯式資料庫的語言，語言執行對關聯式資料庫的檢索和操作。
### 1.何謂關聯式資料庫(RDBMS)
使用多個表格(table)組成，並且可以將資料表關聯起來，連結多個資料表之間關係。
#### 關聯式資料庫的特點
* **table**組成包含**row**代表一筆資料、**column**代表欄位名稱
* **Schema**必須先定義好，並只接受同樣格式的資料插入與修改。但往後需要調整Schema必須對已存在的資料作相對應處理會很麻煩。
* 可以使用 **JOIN** 來連結多個資料表，做較複雜的查詢
* 具備[ACID 特性](https://www.explainthis.io/zh-hant/swe/acid-intro)
* 使用 **SQL**(Structured Querying Language)來管理及查詢資料

### 2.為何 Android Jetpack Room 是關聯式資料庫?
> Room之所以是關聯式資料庫，因為它使用表格儲存數據，每個table都需要定義@PrimaryKey，透過使用SQL進行操作和查詢管理，依靠底層SQLite處理這些操作，使得Room使用起來簡單且高效。
> [Room禁止資料表物件互相參照](https://developer.android.com/training/data-storage/room/referencing-data?hl=zh-tw#understand-no-object-references)，須明確要求應用程式所需的資料

### 3.延伸議題
1. 非關聯型的[NOSQL](https://zh.wikipedia.org/wiki/NoSQL#)，強調Key-value儲存和文件導向資料庫的優點
2. [RDBMS vs NOSQL](https://medium.com/@eric248655665/rdbms-vs-nosql-%E9%97%9C%E8%81%AF%E5%BC%8F%E8%B3%87%E6%96%99%E5%BA%AB-vs-%E9%9D%9E%E9%97%9C%E8%81%AF%E5%BC%8F%E8%B3%87%E6%96%99%E5%BA%AB-1423c9fbb91a)
---

## Room 的三個主要元件
![upload_b1174afd01d4ca1de2ba56974dcbef3c](https://github.com/Quuanna/Jetpack-Room-Exercise/assets/36694083/9181e4a0-4e96-4aa9-a4ad-3d1abca6d365)

### 資料表 Entity
代表應用程式資料庫中的資料表。包含資料庫中對應資料表內每個資料欄的欄位，簡單的實體範例，能夠定義內含 ID、其他資料欄的產品資料表。

![upload_8d0961fb916611037e59b842239ec88f](https://github.com/Quuanna/Jetpack-Room-Exercise/assets/36694083/e34bfe2b-066c-4796-8ecb-74aaf160b076)

**1. Annotations**: 
- 資料表名稱(`@Entity`) 
    - 預設會使用class name做為名稱，@Entity(tableName = "product") 自訂義名稱使用
    - 加快搜尋速度，可將常索引專用的資料欄在 @Entity 註解中加入 indices 屬性，並列出要加入索引或複合式索引的資料欄名稱 @Entity(tableName = "product", indices = [Index(value = ["name"])])
- 資料表ID(`@PrimaryKey`)
    - 可設定自動產生不重複的 id (autoGenerate = true)
- 資料表名稱欄位名稱(`@ColumnInfo`)

---
### 資料存取物件 Data Access Object(DAO)
 - class須為interface、abstract
 - **Room.databaseBuild().build()時預設跑IOThread**
 - DAO查詢時避免main Thread運行會導致畫面卡住，需寫非同步的查詢
 - compile time時，Room自動產生定義的 DAO，和檢查您的SQL是否有語法錯誤
 - Room是基於Android SQLite建置，支援SQL語法，所以Insert、Delete、update時可以使用Annotations，Query可運用SQL語法進行

1. **Annotations**: 
    - 插入(`@Insert`)
    - 刪除(`@Delete`)
    - 更新(`@Update`)
    - 查詢(`@Query`)
   
    ![upload_85b65ddeadb3e58986488e38cad6bffe](https://github.com/Quuanna/Jetpack-Room-Exercise/assets/36694083/71d29908-fdab-4917-be02-5c9cb21277eb)


2.1 非同步查詢-Coroutine +flow
-  suspend DAO非同步操作: Room 2.1 and higher [Coroutine](https://developer.android.com/kotlin/coroutines) 
-  observable DAO及時回覆: Room 2.2 and higher Kotlin[Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/)

---

### 資料庫類別 DataBase
建立資料庫基礎連線的主要存取點，

```
@Database(entities = [Word::class], version = 1)
abstract class WordRoomDataBase : RoomDatabase() {
    abstract fun wordDao(): WordDao

    companion object {
        @Volatile
        private var INSTANCE: WordRoomDataBase? = null
        fun getDatabaseInstance(context: Context): WordRoomDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context = context.applicationContext,
                    klass = WordRoomDataBase::class.java,
                    name = "word_database"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}
```

# 參考資料來源
1. [Define data using Room entities](https://developer.android.com/training/data-storage/room/defining-data)
3. [Accessing data using Room DAOs](https://developer.android.com/training/data-storage/room/accessing-data)
4. [Define relationships between objects](https://developer.android.com/training/data-storage/room/relationships)
5. [Write asynchronous DAO queries](https://developer.android.com/training/data-storage/room/async-queries)
6. [Create views into a database](https://developer.android.com/training/data-storage/room/creating-views)
7. [CodeLab Android Room with a View - Kotlin](https://developer.android.com/codelabs/android-room-with-a-view-kotlin#0)
