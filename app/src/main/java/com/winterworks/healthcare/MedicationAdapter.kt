package com.winterworks.healthcare

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MedicationAdapter(
    private var medications: MutableList<Medication>,
    private val onDeleteClick: (Int) -> Unit
) : RecyclerView.Adapter<MedicationAdapter.MedicationViewHolder>() {

    class MedicationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameText: TextView = view.findViewById(R.id.text_med_name)
        val detailsText: TextView = view.findViewById(R.id.text_med_details)
        val notesText: TextView = view.findViewById(R.id.text_med_notes)
        val deleteBtn: ImageButton = view.findViewById(R.id.button_delete_med)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_medication, parent, false)
        return MedicationViewHolder(view)
    }

    override fun onBindViewHolder(holder: MedicationViewHolder, position: Int) {
        val med = medications[position]

        // 1. Set the Name
        holder.nameText.text = med.name

        // 2. Build the Timing String (Systematic approach)
        val timingList = mutableListOf<String>()
        if (med.isMorning) timingList.add("Morning")
        if (med.isEvening) timingList.add("Evening")
        if (med.isNight) timingList.add("Night")

        // Join the selected slots with a bullet point
        val scheduleStr = timingList.joinToString(" • ")

        // Format the Alarm Time
        val alarmStr = String.format("%02d:%02d", med.hour, med.minute)

        // Combine everything: "Morning • Night | Alarm: 08:00"
        holder.detailsText.text = "$scheduleStr | Alarm: $alarmStr"

        // 3. Set the Notes
        holder.notesText.text = if (med.notes.isNotEmpty()) med.notes else "No additional notes"

        // 4. Handle Delete Button
        holder.deleteBtn.setOnClickListener {
            onDeleteClick(holder.adapterPosition)
        }
    }

    override fun getItemCount() = medications.size

    /**
     * Updates the list and refreshes the UI
     */
    fun updateData(newList: List<Medication>) {
        medications = newList.toMutableList()
        notifyDataSetChanged()
    }
}