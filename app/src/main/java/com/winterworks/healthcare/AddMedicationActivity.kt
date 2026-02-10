package com.winterworks.healthcare

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class AddMedicationActivity : AppCompatActivity() {

    private var selectedHour = 12
    private var selectedMinute = 0
    private lateinit var adapter: MedicationAdapter
    private val localDisplayList = mutableListOf<Medication>()
    private lateinit var database: MedicationDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_medication)

        // Initialize Room Database
        database = MedicationDatabase.getDatabase(this)

        val btnTime = findViewById<Button>(R.id.button_select_time)
        val btnSave = findViewById<Button>(R.id.button_save_med)
        val editName = findViewById<EditText>(R.id.edit_med_name)
        val editNotes = findViewById<EditText>(R.id.edit_notes)

        val cbMorning = findViewById<CheckBox>(R.id.cb_morning)
        val cbEvening = findViewById<CheckBox>(R.id.cb_evening)
        val cbNight = findViewById<CheckBox>(R.id.cb_night)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_medications)
        adapter = MedicationAdapter(localDisplayList) { position ->
            val medToDelete = localDisplayList[position]
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    database.medicationDao().deleteMedication(medToDelete)
                }
                refreshLocalList()
            }
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        refreshLocalList()

        cbMorning.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                selectedHour = 12
                selectedMinute = 0
                btnTime.text = "Time: 12:00 PM"
                cbEvening.isChecked = false
                cbNight.isChecked = false
            }
        }

        cbEvening.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                selectedHour = 16
                selectedMinute = 0
                btnTime.text = "Time: 04:00 PM"
                cbMorning.isChecked = false
                cbNight.isChecked = false
            }
        }

        cbNight.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                selectedHour = 20
                selectedMinute = 0
                btnTime.text = "Time: 08:00 PM"
                cbMorning.isChecked = false
                cbEvening.isChecked = false
            }
        }

        btnTime.setOnClickListener {
            TimePickerDialog(this, { _, hour, minute ->
                selectedHour = hour
                selectedMinute = minute
                val amPm = if (hour < 12) "AM" else "PM"
                val displayHour = if (hour % 12 == 0) 12 else hour % 12
                btnTime.text = String.format("Time: %02d:%02d %s", displayHour, minute, amPm)
            }, selectedHour, selectedMinute, false).show()
        }

        btnSave.setOnClickListener {
            val name = editName.text.toString()
            val notes = editNotes.text.toString()

            val morning = cbMorning.isChecked
            val evening = cbEvening.isChecked
            val night = cbNight.isChecked

            if (name.isNotEmpty() && (morning || evening || night)) {
                val newMed = Medication(
                    name = name,
                    isMorning = morning,
                    isEvening = evening,
                    isNight = night,
                    notes = notes,
                    hour = selectedHour,
                    minute = selectedMinute
                )

                // FIX: Save to Room Database instead of MedicationRepository
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        database.medicationDao().insertMedication(newMed)
                    }

                    scheduleAlarm(newMed)
                    refreshLocalList()

                    // Clear form
                    editName.text.clear()
                    editNotes.text.clear()
                    cbMorning.isChecked = false
                    cbEvening.isChecked = false
                    cbNight.isChecked = false
                    selectedHour = 12
                    selectedMinute = 0
                    btnTime.text = "Set Time"

                    Toast.makeText(this@AddMedicationActivity, "Medication saved to Database!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please enter a name and select a time slot", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun refreshLocalList() {
        lifecycleScope.launch {
            val list = withContext(Dispatchers.IO) {
                database.medicationDao().getAllMedications()
            }
            localDisplayList.clear()
            localDisplayList.addAll(list)
            adapter.notifyDataSetChanged()
        }
    }

    private fun scheduleAlarm(medication: Medication) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!alarmManager.canScheduleExactAlarms()) {
                val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                startActivity(intent)
                return
            }
        }

        val intent = Intent(this, AlarmReceiver::class.java).apply {
            putExtra("MED_NAME", medication.name)
            putExtra("MED_NOTES", medication.notes)
        }

        val requestCode = System.currentTimeMillis().toInt()
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, medication.hour)
            set(Calendar.MINUTE, medication.minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        if (calendar.timeInMillis <= System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        try {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        } catch (e: SecurityException) {
            Toast.makeText(this, "Error scheduling alarm: Permission denied", Toast.LENGTH_SHORT).show()
        }
    }
}