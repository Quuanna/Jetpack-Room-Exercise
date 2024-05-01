package com.example.exercise_room

import android.app.Application
import com.example.exercise_room.database.WordRoomDataBase
import com.example.exercise_room.repo.WordRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MyApplication: Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())
    private val dataBase by lazy { WordRoomDataBase.getDatabaseInstance(this, applicationScope) }

    val repository by lazy { WordRepository(dataBase.wordDao()) }
}