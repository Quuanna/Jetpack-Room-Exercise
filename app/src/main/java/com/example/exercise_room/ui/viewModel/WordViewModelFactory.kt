package com.example.exercise_room.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.exercise_room.repo.WordRepository

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
