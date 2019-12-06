package com.examples2019.database.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.examples2019.database.db.Word

@Dao
interface WordDao {

    @Query("SELECT * from word_table ORDER BY word ASC")
    suspend fun getAlphabetizedWords(): List<Word>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Word)

    @Query("DELETE FROM word_table")
    suspend fun deleteAll()
}