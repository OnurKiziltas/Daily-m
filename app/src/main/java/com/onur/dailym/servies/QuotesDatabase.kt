package com.onur.dailym.servies

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.onur.dailym.model.QuotesModel

@Database(entities = arrayOf(QuotesModel::class),version = 1)
abstract class QuotesDatabase : RoomDatabase() {

    abstract fun QuotesDao(): QuotesDao

    companion object {

        @Volatile private var instance : QuotesDatabase? = null

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock){
            instance ?: makeDatabase(context).also {
                instance = it
            }
        }

        private fun makeDatabase(context : Context) = Room.databaseBuilder(
                context.applicationContext,QuotesDatabase::class.java,"QuotesDatabase"
        ).build()

    }

}