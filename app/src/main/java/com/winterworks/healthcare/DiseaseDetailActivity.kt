package com.winterworks.healthcare

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class DiseaseDetailActivity : AppCompatActivity() {

    companion object {
        // Key used to retrieve the DiseaseInfo object from the Intent
        const val EXTRA_DISEASE_INFO = "disease_info_extra"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_disease_detail)

        // Setup Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // 1. Retrieve the Parcelable object from the Intent
        val diseaseInfo = intent.getParcelableExtra<DiseaseInfo>(EXTRA_DISEASE_INFO)

        if (diseaseInfo != null) {
            // Set the Toolbar title dynamically
            supportActionBar?.title = diseaseInfo.name

            // 2. Find views and populate data
            findViewById<TextView>(R.id.text_disease_name).text = diseaseInfo.name
            findViewById<TextView>(R.id.text_symptoms_content).text = diseaseInfo.symptoms
            findViewById<TextView>(R.id.text_first_aid_content).text = diseaseInfo.firstAid
            findViewById<TextView>(R.id.text_long_term_content).text = diseaseInfo.longTermTreatment
        }
    }

    // Handle the Up button (back arrow) click
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}