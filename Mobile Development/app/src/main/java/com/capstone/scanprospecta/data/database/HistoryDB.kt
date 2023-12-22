package com.capstone.scanprospecta.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [History::class], version = 1)
abstract class HistoryDB : RoomDatabase() {

    abstract fun historyDao(): HistoryDao

    companion object {
        @Volatile
        private var instance: HistoryDB? = null

        fun getInstanca(context: Context): HistoryDB =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(context, HistoryDB::class.java, "history_db").build()
            }.also { instance = it }
    }
}