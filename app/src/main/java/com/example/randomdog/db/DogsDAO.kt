package com.example.randomdog.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dog: Dog)

    @Query("SELECT COUNT(*) FROM dogs")
    fun getCount(): Int

    // Delete the oldest record
    @Query("DELETE FROM dogs WHERE id IN (SELECT id FROM dogs ORDER BY createdAt ASC LIMIT 1)")
    fun deleteOldest()

    @Query("SELECT * FROM dogs ORDER BY createdAt DESC")
    fun getAllDogs(): List<Dog>

    @Query("DELETE FROM dogs")
    fun deleteAllData()
}
