## Room是關聯式資料庫
> Room 是[關聯式資料庫](https://zh.wikipedia.org/zh-tw/%E5%85%B3%E7%B3%BB%E6%95%B0%E6%8D%AE%E5%BA%93) **RDBMS**(Relational Database Management System)的資料庫，建立在[關聯式模型](https://zh.wikipedia.org/wiki/%E5%85%B3%E7%B3%BB%E6%A8%A1%E5%9E%8B)，由資料結構、操作集合、完整性約束三部分組合，使用標準資料查詢語言[SQL](https://zh.wikipedia.org/wiki/SQL)就是一種基於關聯式資料庫的語言，語言執行對關聯式資料庫的檢索和操作。

## Room 特性和優勢
![Room](https://github.com/Quuanna/Jetpack-Room-Exercise/assets/36694083/554e00d9-bc97-486a-bab4-228be905061c)

**優勢**
1. 簡化資料庫操作:Room提供抽象層來降低SQL的複雜性，使用簡單化。
2. 提升應用程式可靠性: Room提供Annotation自動產生SQL語法但Query仍需要一些SQL語法，所以在compile time時檢查 SQL Query 語法，避免在run time時發生閃退，提高穩定可靠性。
3. 確保資料一致性:確保資料庫和資料物件之間是互應的。
4. 提升開發效率:因簡化操作，加快開發流程
5. 易維護:Room使用方式與程式結構清晰，降低維護成本

**特性**
* 可支持測試和IDE Debug
    * JUnit test 可跑在 Android device測試實際狀況
    * IDE Debug可在Android Studio 4.1 以上版本中，Database Inspector
    * Room 資料庫版本間遷移作業測試

**其他特性**
* 很適合運用jetpack提供library一起構建[Reactive programming](https://zh.wikipedia.org/zh-tw/%E5%93%8D%E5%BA%94%E5%BC%8F%E7%BC%96%E7%A8%8B)，例如:viewModel、lineData、Paging等
---

## Room 的三個主要元件
1. 資料表 Entity
2. 資料存取物件 Data Access Object(DAO)
3. 資料庫類別 DataBase
建立資料庫基礎連線的主要存取點，

[更多探討細節 HackMD](https://hackmd.io/@YubUeGjDS8C4yMh0F9Fn1g/HkPLa8ZVA) 
