package com.winterworks.healthcare

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MedicationTrackerActivity : AppCompatActivity() {

    private lateinit var adapter: MedicationAdapter
    private val displayList = mutableListOf<Medication>()
    private lateinit var database: MedicationDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medication_tracker)

        // 1. Initialize Database (Permanent Storage)
        database = MedicationDatabase.getDatabase(this)

        // 2. Setup Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar_medication_tracker)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "My Medications"

        // 3. Setup RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view_medications)

        // Pass the displayList reference and the delete logic
        adapter = MedicationAdapter(displayList) { position ->
            val medicationToDelete = displayList[position]

            // Delete from Room Database
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    database.medicationDao().deleteMedication(medicationToDelete)
                }
                // Update the local list and notify adapter
                refreshList()
                Toast.makeText(this@MedicationTrackerActivity, "${medicationToDelete.name} removed", Toast.LENGTH_SHORT).show()
            }
        }

        // CRITICAL: Ensure layout manager is set before adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // 4. Setup FAB to open the entry form
        val fabAdd: FloatingActionButton = findViewById(R.id.fab_add_medication)
        fabAdd.setOnClickListener {
            val intent = Intent(this, AddMedicationActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * onResume runs every time you return to this screen.
     * It ensures we always fetch the most current data from the database.
     */
    override fun onResume() {
        super.onResume()
        refreshList()
    }

    /**
     * Fetches the latest medication list from Room and updates the UI.
     */
    private fun refreshList() {
        lifecycleScope.launch {
            // Get data from DB on a background thread (IO)
            val medicationsFromDb = withContext(Dispatchers.IO) {
                database.medicationDao().getAllMedications()
            }

            // Clear current display and add fresh items from the database
            displayList.clear()
            displayList.addAll(medicationsFromDb)

            // Update the adapter to reflect the changes
            adapter.notifyDataSetChanged()

            // DEBUG CHECK: Helps you see if data exists in the database
            if (displayList.isEmpty()) {
                Toast.makeText(this@MedicationTrackerActivity, "No medications found. Use + to add.", Toast.LENGTH_SHORT).show()
            } else {
                // Uncomment the line below if you want to see a count every time it loads
                // Toast.makeText(this@MedicationTrackerActivity, "Loaded ${displayList.size} items", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}