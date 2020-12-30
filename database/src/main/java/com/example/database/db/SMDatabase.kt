package com.example.database.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import hr.foi.air.webservice.model.Meal

@Database(
    entities = [Meal::class],
    version = 1
)
abstract class SMDatabase : RoomDatabase(){

    abstract fun getDAO(): DAO

    companion object{
        @Volatile
        private var instance:  SMDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: createDatabase(context).also{  instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                SMDatabase::class.java,
                "smdatabase"
            ).build()
    }
}