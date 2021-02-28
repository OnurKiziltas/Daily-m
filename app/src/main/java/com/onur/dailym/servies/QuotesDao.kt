package com.onur.dailym.servies

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.onur.dailym.model.QuotesModel

@Dao
interface QuotesDao {

    @Insert
    suspend fun insertAll(vararg quotes: QuotesModel) : List<Long>

    @Query("SELECT * FROM QuotesModel")
    suspend fun getQuotes(): List<QuotesModel>

    @Query("DELETE FROM QuotesModel")
    suspend fun deleteQuotes()

}