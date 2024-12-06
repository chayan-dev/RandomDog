package com.example.randomdog.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Dog::class], version = 1)
abstract class DogsDatabase: RoomDatabase() {
    abstract fun getDogsDao():DogDao
}