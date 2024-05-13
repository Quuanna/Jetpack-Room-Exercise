package com.example.exercise_room.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.exercise_room.database.Word
import com.example.exercise_room.repo.WordRepository
import kotlinx.coroutines.launch

class WordViewModel(private val wordRepository: WordRepository) : ViewModel() {


    val allWord: LiveData<List<Word>> = wordRepository.allWord.asLiveData()

    fun insert(word: Word) = viewModelScope.launch {
        wordRepository.insert(word)
    }

    fun editUpdate(oldWord: String, word: Word) = viewModelScope.launch {
        wordRepository.edit(oldWord, word)
    }

    fun delete(word: Word) = viewModelScope.launch {
        wordRepository.delete(word)
    }

    fun deleteAll() = viewModelScope.launch {
        wordRepository.deleteAll()
    }

}
