package com.winterworks.healthcare

import androidx.room.*

/**
 * DAO (Data Access Object) defines the methods to interact with the database.
 */
@Dao
interface MedicationDao {

    @Query("SELECT * FROM medications")
    suspend fun getAllMedications(): List<Medication>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedication(medication: Medication)

    @Delete
    suspend fun deleteMedication(medication: Medication)
}