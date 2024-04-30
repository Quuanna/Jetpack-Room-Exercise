package com.example.exercise_room

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MyApplication: Application() {

    val applicationScope = CoroutineScope(SupervisorJob()) // 無需取消此範圍，因為它會隨著該過程而被拆除

    // 使用by lazy，等需要時才創立
    val dataBase by lazy { WordRoomDataBase.getDatabaseInstance(this, applicationScope) }
    val repository by lazy { WordRepository(dataBase.wordDao()) }
}