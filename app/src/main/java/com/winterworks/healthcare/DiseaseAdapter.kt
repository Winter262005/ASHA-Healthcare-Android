package com.winterworks.healthcare

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Adapter for the RecyclerView in SymptomCheckerActivity.
 * Handles binding DiseaseInfo objects to list_item_disease.xml views.
 */
class DiseaseAdapter(
    private val diseaseList: List<DiseaseInfo>, // The original list of data
    private val onItemClicked: (DiseaseInfo) -> Unit // Function to handle clicks
) : RecyclerView.Adapter<DiseaseAdapter.DiseaseViewHolder>() {

    // Internal copy of the list used for filtering/searching
    private var filteredList: List<DiseaseInfo> = diseaseList.toList()

    // ViewHolder class: holds the views for one list item
    class DiseaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // IDs must match list_item_disease.xml
        val nameTextView: TextView = itemView.findViewById(R.id.text_disease_name)
        val symptomsTextView: TextView = itemView.findViewById(R.id.text_disease_symptoms)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiseaseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_disease, parent, false)
        return DiseaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: DiseaseViewHolder, position: Int) {
        val disease = filteredList[position]
        holder.nameTextView.text = disease.name
        // Show a truncated summary of the symptoms
        holder.symptomsTextView.text = "Symptoms: ${disease.symptoms.take(60)}..."

        // Set the click listener for the entire item view
        holder.itemView.setOnClickListener {
            onItemClicked(disease)
        }
    }

    override fun getItemCount(): Int = filteredList.size

    /**
     * Updates the RecyclerView data based on the user's search query.
     */
    fun filter(query: String) {
        val lowercaseQuery = query.lowercase()
        filteredList = if (query.isBlank()) {
            diseaseList // If query is empty, show the full list
        } else {
            // Filter the original list by disease name or symptoms
            diseaseList.filter {
                it.name.lowercase().contains(lowercaseQuery) ||
                        it.symptoms.lowercase().contains(lowercaseQuery)
            }
        }
        notifyDataSetChanged() // Tell the RecyclerView to redraw itself
    }
}