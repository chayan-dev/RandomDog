package com.example.randomdog.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dogs")
data class Dog(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val url: String,
    val createdAt: Long = System.currentTimeMillis()
)
