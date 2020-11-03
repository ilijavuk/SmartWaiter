package com.example.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.database.entities.User

@Dao
interface MyDAO {

    @Insert
    fun addUser(user: User)

    @Query("SELECT * FROM  users ORDER BY id ASC")
    fun readAllData(): List<User>
}