package com.winterworks.healthcare

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SymptomCheckerActivity : AppCompatActivity() {

    private lateinit var diseaseAdapter: DiseaseAdapter
    private val allDiseases = createDummyData() // Static list of all diseases

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_symptom_checker)

        // Setup Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar_symptom_checker)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Symptom Checker"

        // 1. Setup RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view_diseases)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize the adapter with the data and a click handler
        diseaseAdapter = DiseaseAdapter(allDiseases) { selectedDisease ->
            // ACTION: Launch the DiseaseDetailActivity and pass the selected object
            val intent = Intent(this, DiseaseDetailActivity::class.java).apply {
                putExtra(DiseaseDetailActivity.EXTRA_DISEASE_INFO, selectedDisease)
            }
            startActivity(intent)
        }
        recyclerView.adapter = diseaseAdapter

        // 2. Setup Search View
        val searchView: SearchView = findViewById(R.id.search_symptom)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            // Called when the user submits the search query
            override fun onQueryTextSubmit(query: String?): Boolean {
                diseaseAdapter.filter(query.orEmpty())
                searchView.clearFocus() // Hide the keyboard
                return true
            }

            // Called when the search query text changes (live filtering)
            override fun onQueryTextChange(newText: String?): Boolean {
                diseaseAdapter.filter(newText.orEmpty())
                return true
            }
        })
    }

    // Handle the Up button (back arrow) click
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    // Static function to create the core data set
    // Static function to create the core data set
    // Static function to create the core data set
    private fun createDummyData(): List<DiseaseInfo> {
        return listOf(
            // --- A ---
            DiseaseInfo(
                "Acne (Mild to Moderate)",
                "Pimples, blackheads, whiteheads, or small, tender red bumps, mainly on the face, chest, or back.",
                "Wash affected areas twice daily with a mild cleanser. Use over-the-counter benzoyl peroxide or salicylic acid treatments.",
                "Maintain a consistent skincare routine. Avoid picking blemishes. Consult a dermatologist if condition is severe or over-the-counter treatments fail."
            ),
            DiseaseInfo(
                "Allergic Reaction (Mild)",
                "Itching, hives (raised, red patches), watery eyes, sneezing, or nasal congestion.",
                "Identify and avoid the trigger. Take an over-the-counter (OTC) oral antihistamine (e.g., Cetirizine or Diphenhydramine). Apply a cold compress to the affected skin.",
                "Consult a doctor or allergist for testing and a prescription treatment plan if allergies are persistent or severe."
            ),
            DiseaseInfo(
                "Anemia (Mild Iron Deficiency)",
                "Fatigue, weakness, pale skin, shortness of breath, headache.",
                "Increase intake of iron-rich foods (red meat, spinach, beans, fortified cereals) and Vitamin C (helps iron absorption).",
                "Consult a doctor for blood tests and determination of iron supplementation needs. Do not self-diagnose or self-medicate iron supplements long-term."
            ),
            DiseaseInfo(
                "Ankle Sprain (Mild)",
                "Pain, swelling, bruising, and inability to put full weight on the ankle.",
                "Follow **R.I.C.E.** protocol: **R**est, **I**ce (20 min on, 40 min off), **C**ompression (bandage), **E**levation (above heart level).",
                "Over-the-counter pain relief (like Ibuprofen). Start gentle movement after 48 hours. Use crutches if needed. See a doctor if unable to walk at all."
            ),
            DiseaseInfo(
                "Athlete's Foot (Tinea Pedis)",
                "Itching, stinging, and burning between the toes or on the soles of the feet. Redness and flaking skin.",
                "Wash and dry feet thoroughly, especially between the toes. Apply OTC antifungal powder or cream.",
                "Wear clean socks daily and sandals in public showers/pools. Use antifungal powder in shoes. If infection persists or spreads, see a doctor."
            ),
            // --- B ---
            DiseaseInfo(
                "Bronchitis (Acute)",
                "Persistent cough, production of clear, yellow, or green mucus, chest soreness, fatigue, mild fever.",
                "Rest, stay hydrated (water, clear fluids), use a humidifier, and take OTC cough suppressants or expectorants.",
                "Avoid smoke and irritants. Bronchitis is often viral and resolves itself. If coughing persists beyond 3 weeks or fever is high, see a doctor."
            ),
            DiseaseInfo(
                "Bruise (Contusion)",
                "Discoloration of the skin resulting from trauma to the area, swelling, tenderness.",
                "Apply a cold compress or ice pack (wrapped in a cloth) to the area for 15-20 minutes several times a day for the first 24-48 hours. Elevate the injured limb.",
                "Pain relievers (Acetaminophen/Paracetamol) can be used. Consult a doctor if bruising is severe, occurs easily without known trauma, or persists for weeks."
            ),
            // --- C ---
            DiseaseInfo(
                "Carpal Tunnel Syndrome (Early)",
                "Numbness, tingling, weakness in the hand and fingers (especially thumb, index, middle). Pain often worse at night.",
                "Wear a wrist splint (brace) at night to keep the wrist straight. Use cold packs. Take breaks from repetitive hand motions.",
                "Physical therapy or anti-inflammatory drugs. Consult a doctor if symptoms worsen or interfere with daily activities; may require surgical release."
            ),
            DiseaseInfo(
                "Cold Sore (Herpes Simplex)",
                "Small, painful blisters, typically on the lips or around the mouth, that crust over.",
                "Apply an over-the-counter docosanol cream (Abreva) at the first sign of tingling. Keep the area clean and dry.",
                "Avoid touching the area. Use prescription antiviral creams/pills for frequent outbreaks. Avoid sharing utensils/towels."
            ),
            DiseaseInfo(
                "Common Cold (Viral Infection)",
                "Runny nose, sneezing, sore throat, cough, mild fever.",
                "Rest, stay hydrated (water, clear fluids), use saline nasal spray, over-the-counter pain relievers (Paracetamol).",
                "Viral infections resolve themselves. Focus on immune support and hygiene to prevent spread."
            ),
            DiseaseInfo(
                "Conjunctivitis (Pink Eye - Viral)",
                "Redness, itching, and watering of the eye; clear, watery discharge; often starts in one eye and spreads to the other.",
                "Do not touch the eyes. Use cold compresses to soothe. Wash hands frequently. Do not share towels.",
                "Highly contagious; stay home until symptoms resolve. Viral conjunctivitis resolves on its own within 1-2 weeks. See a doctor if vision changes."
            ),
            DiseaseInfo(
                "Constipation",
                "Fewer than three bowel movements per week; hard, dry stools; difficulty or pain during bowel movements; feeling of incomplete evacuation.",
                "Increase fiber intake (fruits, vegetables, whole grains), drink plenty of water, and try a mild OTC laxative.",
                "Maintain a high-fiber diet and regular exercise. Consult a doctor if persistent."
            ),
            DiseaseInfo(
                "Cough (Persistent Dry)",
                "A non-productive cough lasting more than a week, often worse at night.",
                "Use a humidifier, drink warm liquids with honey, and take OTC cough suppressants.",
                "Identify and eliminate environmental triggers (smoke, dust). If persistent or accompanied by wheezing or shortness of breath, consult a doctor."
            ),
            DiseaseInfo(
                "Croup (Mild)",
                "Harsh, barking cough (often worse at night), high-pitched, squeaky breathing (stridor). Common in young children.",
                "Calm the child and expose them to cool air (e.g., outside for a few minutes). Use a cool-mist humidifier.",
                "Keep the child hydrated. If breathing difficulty is severe or rapid, seek emergency medical care immediately."
            ),
            // --- D ---
            DiseaseInfo(
                "Dehydration (Mild)",
                "Thirst, dry mouth/skin, dark yellow urine, fatigue, dizziness.",
                "Sip small amounts of water or an oral rehydration solution (ORS). Avoid sugary drinks.",
                "Continue fluid intake and monitor urine color. If symptoms worsen (e.g., inability to keep fluids down, disorientation), seek medical help."
            ),
            DiseaseInfo(
                "Diarrhea (Acute)",
                "Loose, watery stools occurring three or more times in a day; abdominal cramping or pain.",
                "Prevent dehydration by drinking ORS or electrolyte-rich fluids. Follow the BRAT diet (Bananas, Rice, Applesauce, Toast). Avoid dairy and spicy food.",
                "Avoid anti-diarrheal medication unless directed by a doctor. Seek help if diarrhea lasts over 48 hours or contains blood."
            ),
            DiseaseInfo(
                "Dizziness/Vertigo (Mild)",
                "Feeling faint, woozy, unstable, or having a sensation that the room is spinning.",
                "Lie down immediately, or sit down and hold onto something stable. Drink water. Avoid sudden position changes.",
                "Consult a doctor to rule out underlying causes (e.g., ear infection, medication side effects). Maintain proper hydration and nutrition."
            ),
            // --- E ---
            DiseaseInfo(
                "Earache (Otitis Media - Early)",
                "Pain in the ear, often associated with a cold or flu; muffled hearing.",
                "Use OTC pain relievers (Ibuprofen/Paracetamol). Apply a warm, moist cloth to the outer ear. Avoid getting water in the ear.",
                "If pain persists, fever develops, or hearing loss increases, see a doctor for diagnosis (often requires prescription antibiotics)."
            ),
            DiseaseInfo(
                "Eczema (Dermatitis)",
                "Dry, red, itchy patches of skin, sometimes with small bumps.",
                "Apply a thick, unscented moisturizer immediately after bathing while skin is still damp. Use a cool compress to relieve itching. Avoid harsh soaps.",
                "Identify and avoid triggers (e.g., certain detergents, fabrics). Use prescribed topical steroids or calcineurin inhibitors as directed by a dermatologist."
            ),
            // --- F ---
            DiseaseInfo(
                "Fatigue (Non-Chronic)",
                "Persistent tiredness, low energy, and feeling run down, not relieved by sleep.",
                "Ensure consistent sleep (7-9 hours). Focus on balanced diet, hydration, and light exercise.",
                "If fatigue is sudden, severe, or lasts longer than two weeks, consult a doctor to check for underlying medical conditions (e.g., thyroid issues, vitamin deficiencies, anemia)."
            ),
            DiseaseInfo(
                "Fever (Low Grade)",
                "Body temperature above 98.6°F (37°C) but below 100.4°F (38°C), chills, mild sweating, general malaise.",
                "Rest, cool compresses, drink plenty of fluids (water or electrolyte drinks). Wear light clothing.",
                "Monitor temperature regularly. If fever exceeds 102°F (38.9°C), persists for more than 48 hours, or is accompanied by severe symptoms, seek medical advice."
            ),
            DiseaseInfo(
                "Food Poisoning (Mild)",
                "Nausea, vomiting, diarrhea, and stomach cramps, usually within hours of eating contaminated food.",
                "Rest, sip clear liquids (ORS) to replace lost fluids. Avoid solid food until vomiting stops. Do not take anti-diarrheal medicine unless advised.",
                "Gradually return to bland foods (BRAT). Seek immediate medical attention if symptoms are severe, include bloody stool, or signs of severe dehydration."
            ),
            // --- G ---
            DiseaseInfo(
                "Gastroenteritis (Stomach Flu)",
                "Nausea, vomiting, diarrhea, abdominal cramps, low-grade fever.",
                "Rest, sip small amounts of clear fluids (water, ORS) frequently. Avoid solid food until vomiting stops. Do not take anti-diarrheal medicine unless advised.",
                "Gradually return to a bland diet (BRAT). Good hygiene prevents spread. Seek medical attention if vomiting persists or signs of severe dehydration appear."
            ),
            DiseaseInfo(
                "Gastroesophageal Reflux (Heartburn)",
                "Burning sensation in the chest, often after eating; sour or bitter taste in the throat.",
                "Take an OTC antacid. Avoid lying down immediately after meals. Loosen restrictive clothing.",
                "Avoid trigger foods (spicy, fatty, caffeine, alcohol). Eat smaller, more frequent meals. Raise the head of the bed while sleeping."
            ),
            // --- H ---
            DiseaseInfo(
                "Hay Fever (Allergic Rhinitis)",
                "Sneezing, itchy/watery eyes, nasal congestion, and runny nose, typically seasonal.",
                "Take oral antihistamines (e.g., Cetirizine). Use saline nasal spray. Minimize exposure to pollen (keep windows closed).",
                "Consult an allergist for prescription nasal sprays or immunotherapy (allergy shots) if symptoms are disruptive."
            ),
            DiseaseInfo(
                "Headache (Migraine)",
                "Severe throbbing or pulsing pain, usually on one side of the head, sensitivity to light/sound, nausea, vomiting.",
                "Rest immediately in a dark, quiet room. Apply a cold compress to the forehead or neck. OTC pain relievers (Ibuprofen, Acetaminophen).",
                "Identify triggers (diet, stress, lack of sleep). Prescription abortive medications (triptans) may be needed. Consult a doctor for diagnosis and management plan."
            ),
            DiseaseInfo(
                "Headache (Tension)",
                "Dull, aching pain felt across the forehead, sides, or back of the head, often described as a tight band.",
                "Rest in a dark, quiet room. Apply a warm or cool compress to the forehead or neck. Drink water.",
                "Over-the-counter pain relievers (Paracetamol or Ibuprofen). Practice stress management and ensure adequate sleep. If headaches become chronic or sudden and severe, consult a doctor."
            ),
            // --- I ---
            DiseaseInfo(
                "Indigestion (Dyspepsia)",
                "Upper abdominal pain or discomfort, bloating, early fullness during a meal, or a burning sensation.",
                "Avoid eating large meals. Reduce fatty foods, caffeine, and alcohol. Use OTC antacids.",
                "Identify and avoid trigger foods. If symptoms are persistent, accompanied by weight loss, or difficulty swallowing, see a doctor."
            ),
            DiseaseInfo(
                "Ingrown Toenail",
                "Pain, tenderness, redness, and swelling where the nail edge grows into the skin.",
                "Soak the foot in warm, soapy water 3-4 times a day. Gently lift the nail edge and place a small piece of cotton or floss underneath.",
                "Wear well-fitting shoes. If pain or infection (pus) persists, see a podiatrist or doctor for safe removal."
            ),
            DiseaseInfo(
                "Insect Bite/Sting (General)",
                "Redness, swelling, and itching at the site.",
                "Remove stinger if present. Wash area with soap and water. Apply ice pack and OTC anti-itch cream (hydrocortisone or calamine lotion).",
                "Monitor for signs of infection or allergic reaction. Severe swelling or difficulty breathing requires immediate medical help."
            ),
            DiseaseInfo(
                "Insomnia (Short-term)",
                "Difficulty falling asleep, staying asleep, or poor quality sleep for less than 3 months.",
                "Practice good sleep hygiene: establish a routine, ensure a dark, cool room, and avoid screens/caffeine before bed.",
                "Avoid alcohol and heavy meals late at night. If persistent, consult a doctor or therapist for cognitive behavioral therapy for insomnia (CBT-I)."
            ),
            // --- L ---
            DiseaseInfo(
                "Lumbago (Lower Back Pain - Acute)",
                "Sudden, sharp pain in the lower back, often triggered by lifting or twisting.",
                "Apply heat (heating pad) or cold (ice pack) for 15-20 minutes. OTC pain relievers (Ibuprofen). Avoid heavy lifting.",
                "Gentle stretching and walking. If pain radiates down the leg, causes numbness, or does not improve after a few days, consult a doctor/physiotherapist."
            ),
            // --- M ---
            DiseaseInfo(
                "Menstrual Cramps (Dysmenorrhea)",
                "Painful cramps in the lower abdomen, often spreading to the back and thighs, occurring before or during menstruation.",
                "Apply a heating pad to the abdomen or lower back. Take OTC nonsteroidal anti-inflammatory drugs (NSAIDs) like Ibuprofen.",
                "Light exercise, yoga, and stress reduction can help. If pain is debilitating or accompanied by very heavy bleeding, consult a gynecologist."
            ),
            DiseaseInfo(
                "Minor Burn (First Degree)",
                "Redness, pain, and swelling limited to the outermost layer of skin (no blistering).",
                "Run the area under cool (not cold) running water for at least 10-15 minutes. Apply aloe vera or a moisturizer. Do not use ice.",
                "Keep the area clean. Protect from the sun. If blisters form or pain worsens, seek medical help."
            ),
            DiseaseInfo(
                "Minor Cut/Injury",
                "Bleeding, pain, break in skin, swelling.",
                "Wash hands, gently clean wound with soap and water, apply antiseptic cream, cover with sterile bandage.",
                "Change bandage daily. Monitor for signs of infection (redness, warmth, pus). Consult doctor if bleeding is severe or wound is deep."
            ),
            DiseaseInfo(
                "Muscle Cramp (Charley Horse)",
                "Sudden, involuntary, and painful contraction of a muscle, usually in the leg.",
                "Gently stretch and massage the cramped muscle. Apply heat to relax the muscle.",
                "Ensure adequate hydration and electrolyte (magnesium, potassium) intake. Regular stretching before and after exercise."
            ),
            // --- N ---
            DiseaseInfo(
                "Nosebleed (Epistaxis)",
                "Bleeding from one or both nostrils.",
                "Sit upright and lean slightly forward (do NOT lean back). Pinch the soft part of the nose just above the nostrils for 10-15 minutes. Breathe through the mouth. Apply a cold compress to the bridge of the nose.",
                "Avoid nose picking and strenuous activity for 24 hours. Use saline spray or a humidifier to keep nasal passages moist. Seek help if bleeding lasts >20 minutes or is due to trauma."
            ),
            // --- P ---
            DiseaseInfo(
                "Pharyngitis (Viral)",
                "Sore, scratchy throat, usually accompanied by cold symptoms (runny nose, cough).",
                "Gargle with warm salt water. Drink warm liquids (tea with honey). Suck on lozenges or hard candy.",
                "Rest the voice. Use OTC pain relievers (Ibuprofen). Consult a doctor if symptoms worsen, last longer than 5 days, or include a high fever without cold symptoms."
            ),
            DiseaseInfo(
                "Poison Ivy/Oak/Sumac Exposure",
                "Itchy, red skin rash, sometimes with blisters, where the skin touched the plant oil (urushiol).",
                "Immediately wash the area with soap and cool water (or rubbing alcohol) to remove oil. Use a cool compress and calamine lotion or hydrocortisone cream to relieve itching.",
                "Avoid scratching. OTC oral antihistamines may help. See a doctor if the rash is widespread, severe, or near the eyes/mouth."
            ),
            // --- R ---
            DiseaseInfo(
                "Ringworm (Tinea)",
                "Itchy, red, circular rash with clearer skin in the middle (ring shape).",
                "Wash the area and keep it dry. Apply OTC antifungal creams or lotions (e.g., Miconazole).",
                "Avoid sharing towels or clothes. Continue treatment for 2-4 weeks. If the rash does not improve, is extensive, or is on the scalp, consult a doctor."
            ),
            DiseaseInfo(
                "Rusted Metal Contact (Tetanus Risk)",
                "Puncture wound or deep cut from contaminated object (e.g., rusty nail).",
                "Clean the wound thoroughly, apply antiseptic. Determine tetanus vaccination status immediately.",
                "Seek immediate medical attention for a Tetanus shot (Tdap/TT) if the last one was more than 5-10 years ago, or if uncertain. Tetanus is serious."
            ),
            // --- S ---
            DiseaseInfo(
                "Sciatica (Mild)",
                "Pain that radiates from the lower back down through the buttocks and leg; often feels like a sharp or burning sensation.",
                "Apply heat or cold to the painful area. Continue light activity (avoid prolonged sitting). OTC NSAIDs (Ibuprofen).",
                "Focus on gentle stretches and core strengthening exercises. Consult a physiotherapist or doctor if pain is severe or causes numbness/weakness in the leg."
            ),
            DiseaseInfo(
                "Sinusitis (Viral/Mild)",
                "Facial pain/pressure, nasal congestion, runny nose, reduced sense of smell, sometimes fever.",
                "Use saline nasal washes/sprays. Apply a warm compress to the face. Breathe steam (e.g., in a warm shower). Use OTC pain relievers.",
                "Avoid environmental irritants. If symptoms persist for more than 10 days, or if fever is high/pain is severe, see a doctor, as it may be bacterial."
            ),
            DiseaseInfo(
                "Sleep Apnea (Suspected)",
                "Loud snoring, periods of breathing cessation during sleep (reported by partner), morning headache, excessive daytime sleepiness.",
                "Avoid alcohol and sedatives before bed. Try sleeping on your side. Maintain a healthy weight.",
                "Requires diagnosis by a sleep specialist (sleep study). Treatment often involves Continuous Positive Airway Pressure (CPAP) machine or dental devices."
            ),
            DiseaseInfo(
                "Snakebite (Non-Venomous)",
                "Two small puncture marks, minimal pain, no swelling or severe symptoms.",
                "Stay calm, wash the wound gently with soap and water, cover with a clean dressing. Seek medical confirmation if possible.",
                "Keep area clean. Monitor the site for any delayed reaction. Rest."
            ),
            DiseaseInfo(
                "Snakebite (Venomous/Unknown)",
                "Severe pain, rapid swelling, discoloration, nausea, vomiting, dizziness, difficulty breathing.",
                "Call emergency services (e.g., 911/112/108) immediately. Keep the victim calm and still. Keep the bite area below the level of the heart. DO NOT cut the wound or try to suck out venom.",
                "Requires immediate and specialized hospital care (antivenom)."
            ),
            DiseaseInfo(
                "Sore Throat (Viral)",
                "Pain or scratchiness in the throat, often accompanied by pain upon swallowing.",
                "Gargle with warm salt water. Drink warm liquids (tea with honey). Suck on lozenges or hard candy.",
                "Rest the voice. Use OTC pain relievers (Ibuprofen). Consult a doctor if symptoms worsen, last longer than 5 days, or include a high fever."
            ),
            DiseaseInfo(
                "Stomach Ulcer (Suspected)",
                "Burning stomach pain (often worse between meals or at night), bloating, heartburn, nausea.",
                "Avoid spicy/acidic foods, caffeine, and alcohol. Use OTC antacids or proton pump inhibitors (PPIs) for temporary relief.",
                "Requires medical diagnosis (usually endoscopy). Treatment involves prescription medication (antibiotics, stronger PPIs). Stop taking NSAIDs (Ibuprofen) if possible."
            ),
            DiseaseInfo(
                "Sunburn (Mild)",
                "Redness, pain, warmth, and slight swelling on the skin.",
                "Move out of the sun immediately. Cool the affected skin with a cool bath, shower, or damp cloth. Apply aloe vera or a moisturizer.",
                "Avoid further sun exposure. Use broad-spectrum sunscreen with high SPF daily. Do not pop any blisters (if present, seek advice)."
            ),
            // --- T ---
            DiseaseInfo(
                "Tendonitis (Mild)",
                "Pain and tenderness near a joint (shoulder, elbow, knee) that worsens with activity.",
                "Rest the injured area. Apply ice for 15 minutes several times a day. OTC NSAIDs (e.g., Naproxen or Ibuprofen).",
                "Avoid activities that aggravate the pain. Gentle stretching. Consult a physiotherapist if symptoms do not improve within a week."
            ),
            DiseaseInfo(
                "Tension Headache",
                "Dull, aching pain felt across the forehead, sides, or back of the head, often described as a tight band.",
                "Rest in a dark, quiet room. Apply a warm or cool compress to the forehead or neck. Drink water.",
                "Over-the-counter pain relievers (Paracetamol or Ibuprofen). Practice stress management and ensure adequate sleep. If headaches become chronic or sudden and severe, consult a doctor."
            ),
            DiseaseInfo(
                "Tinea Versicolor",
                "Patches of skin discoloration (lighter or darker than the surrounding skin), usually on the back, chest, or neck. Often flaky.",
                "Apply an OTC antifungal shampoo (containing selenium sulfide) or cream as directed.",
                "The skin color may take several months to normalize after treatment. Recurrence is common; preventative use of antifungal soap may be recommended."
            ),
            // --- U ---
            DiseaseInfo(
                "Urinary Tract Infection (UTI - Mild)",
                "Frequent urge to urinate, painful burning sensation during urination, cloudy or strong-smelling urine.",
                "Drink plenty of water to flush bacteria. Avoid caffeine and alcohol. OTC pain relievers (Paracetamol) for discomfort.",
                "Requires a doctor's visit for diagnosis and a course of prescription antibiotics. If left untreated, infection can spread to the kidneys."
            ),
            // --- V ---
            DiseaseInfo(
                "Vomiting (Acute)",
                "Forceful expulsion of stomach contents. May be accompanied by nausea.",
                "Rest the stomach; stop eating/drinking for a few hours. Sip small amounts of clear fluids (water, clear broth) frequently to prevent dehydration.",
                "Gradually introduce bland foods (BRAT diet). Seek medical attention if vomiting lasts longer than 24 hours, or is accompanied by severe abdominal pain or signs of dehydration (e.g., reduced urination)."
            ),
            // --- W ---
            DiseaseInfo(
                "Wart (Common)",
                "Small, grainy skin growths, usually on the hands or feet, often rough to the touch.",
                "Use OTC salicylic acid treatments (liquid or patches) applied daily as directed. Cover the wart with duct tape for a week, then file it down.",
                "Warts can take months to disappear. Avoid shaving over warts. For persistent warts, a dermatologist can use cryotherapy (freezing) or surgical removal."
            )
        ).sortedBy { it.name } // GUARANTEES the list is sorted alphabetically by name
    }
}