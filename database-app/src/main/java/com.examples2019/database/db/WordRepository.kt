package com.examples2019.database.db

import com.examples2019.database.db.Word
import com.examples2019.database.db.WordDao

class WordRepository(private val wordDao: WordDao) {

    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }

    suspend fun getWords() = wordDao.getAlphabetizedWords()
}