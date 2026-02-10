package com.winterworks.healthcare

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * The @Entity tag tells Room to create a table for this class.
 * We've added a PrimaryKey ID which is required for database operations.
 */
@Parcelize
@Entity(tableName = "medications")
data class Medication(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val isMorning: Boolean,
    val isEvening: Boolean,
    val isNight: Boolean,
    val notes: String,
    val hour: Int,
    val minute: Int
) : Parcelable