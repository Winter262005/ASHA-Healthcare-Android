package com.winterworks.healthcare

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        val navView: NavigationView = findViewById(R.id.nav_view)

        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)

        // --- HOMEPAGE BUTTON NAVIGATION ---
        findViewById<Button>(R.id.button_symptom_checker).setOnClickListener {
            launchSymptomChecker()
        }

        findViewById<Button>(R.id.button_medication_tracker).setOnClickListener {
            launchMedicationTracker()
        }
    }

    private fun launchSymptomChecker() {
        val intent = Intent(this, SymptomCheckerActivity::class.java)
        startActivity(intent)
    }

    private fun launchMedicationTracker() {
        val intent = Intent(this, MedicationTrackerActivity::class.java)
        startActivity(intent)
    }

    override fun onNavigationItemSelected(item: android.view.MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> showToast("Home")
            R.id.nav_symptom_checker -> launchSymptomChecker()
            R.id.nav_medication -> launchMedicationTracker()
            R.id.nav_appointments -> showToast("Appointments (Coming Soon)")
            R.id.nav_settings -> showToast("Settings")
            R.id.nav_logout -> showToast("Logout")
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}