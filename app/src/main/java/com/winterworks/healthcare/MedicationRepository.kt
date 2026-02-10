package com.winterworks.healthcare

/**
 * A simple shared storage for medications during the current session.
 */
object MedicationRepository {
    private val medications = mutableListOf<Medication>()

    fun addMedication(med: Medication) {
        medications.add(med)
    }

    fun getAllMedications(): List<Medication> {
        return medications
    }

    fun removeMedication(position: Int) {
        if (position in medications.indices) {
            medications.removeAt(position)
        }
    }
}