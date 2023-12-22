package com.capstone.scanprospecta.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HistoryDao {

    @Query("SELECT * FROM history")
    fun getHistory(): LiveData<List<History>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertHistory(history: List<History>)
}