package com.winterworks.healthcare

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Data class representing a medical condition or ailment, including its treatment plan.
 * Marked as Parcelable to allow passing the object between Activities via Intent.
 */
@Parcelize
data class DiseaseInfo(
    val name: String,
    val symptoms: String,
    val firstAid: String,
    val longTermTreatment: String
) : Parcelable