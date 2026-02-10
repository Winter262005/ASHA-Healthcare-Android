package com.winterworks.healthcare

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * The main Database class for the app.
 */
@Database(entities = [Medication::class], version = 1, exportSchema = false)
abstract class MedicationDatabase : RoomDatabase() {

    abstract fun medicationDao(): MedicationDao

    companion object {
        @Volatile
        private var INSTANCE: MedicationDatabase? = null

        fun getDatabase(context: Context): MedicationDatabase {
            // If the instance is not null, return it. Otherwise, create it.
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MedicationDatabase::class.java,
                    "medication_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}