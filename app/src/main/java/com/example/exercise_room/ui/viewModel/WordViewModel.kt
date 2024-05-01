package com.example.exercise_room.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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
}

class WordViewModelFactory(private val wordRepository: WordRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            WordViewModel(wordRepository) as T
        } else {
            super.create(modelClass)
        }
    }
}
