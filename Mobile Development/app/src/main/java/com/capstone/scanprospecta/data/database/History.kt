package com.capstone.scanprospecta.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable



@Entity (tableName = "history")
data class History(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "fileName")
    var fileName: String,

    @ColumnInfo(name = "photoUrl")
    var photoUrl: String,

    @ColumnInfo(name = "jobRecom")
    var jobRecom: String,

) : Serializable