package com.example.exercise_room.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.exercise_room.database.Word
import com.example.exercise_room.repo.WordRepositoryImpl
import kotlinx.coroutines.launch

class WordViewModel(private val wordRepository: WordRepositoryImpl) : ViewModel() {

    val allWord: LiveData<List<Word>> = wordRepository.allWords.asLiveData()

    fun insert(word: Word) = viewModelScope.launch {
        wordRepository.insert(word)
    }

    fun editUpdate(word: Word) = viewModelScope.launch {
        wordRepository.update(word)
    }

    fun delete(word: Word) = viewModelScope.launch {
        wordRepository.delete(word)
    }

    fun deleteAll() = viewModelScope.launch {
        wordRepository.deleteAll()
    }

}
