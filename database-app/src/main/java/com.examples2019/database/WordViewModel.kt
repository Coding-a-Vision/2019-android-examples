package com.examples2019.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.examples2019.database.db.Word
import com.examples2019.database.db.WordRepository
import com.examples2019.database.db.WordRoomDatabase
import kotlinx.coroutines.launch

class WordViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: WordRepository
    val allWords: MutableLiveData<List<Word>> = MutableLiveData()

    init {
        val wordsDao = WordRoomDatabase.getDatabase(application).wordDao()
        repository = WordRepository(wordsDao)
        updateWords()
    }

    private fun updateWords() = viewModelScope.launch {
        allWords.postValue(repository.getWords())
    }

    fun insert(word: Word) {
        viewModelScope.launch {
            repository.insert(word)
            updateWords()
        }
    }
}