package com.example.exercise_room.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.exercise_room.repo.WordRepositoryImpl

class WordViewModelFactory(private val wordRepository: WordRepositoryImpl): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            WordViewModel(wordRepository) as T
        } else {
            super.create(modelClass)
        }
    }
}
