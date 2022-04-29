package com.example.weatherproject.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val city: String,
    val temperature: Int,
    val condition: String
)
