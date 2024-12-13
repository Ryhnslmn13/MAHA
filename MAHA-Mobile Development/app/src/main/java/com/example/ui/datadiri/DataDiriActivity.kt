package com.example.ui.datadiri

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mahaapp.R
import com.google.firebase.auth.FirebaseAuth
import com.example.mahaapp.MainActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DataDiriActivity : AppCompatActivity() {

    private lateinit var edtName: EditText
    private lateinit var edtAge: EditText
    private lateinit var edtWeight: EditText
    private lateinit var edtHeight: EditText
    private lateinit var rgGender: RadioGroup
    private lateinit var rbMale: RadioButton
    private lateinit var rbFemale: RadioButton
    private lateinit var rgDiabetes: RadioGroup
    private lateinit var rgHypertension: RadioGroup
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_diri)

        database =
            FirebaseDatabase.getInstance("https://maha-app-443810-default-rtdb.asia-southeast1.firebasedatabase.app/").reference

        val currentUser  = FirebaseAuth.getInstance().currentUser
        if (currentUser  == null || currentUser .uid.isNullOrEmpty()) {
            Toast.makeText(this, "No user is logged in", Toast.LENGTH_SHORT).show()
            return
        }

        val uid = currentUser .uid!!

        database.child("users").child(uid).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val userData = task.result.value as? Map<String, Any>
                if (userData != null) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    initializeViews()
                }
            } else {
                Toast.makeText(this, "Failed to retrieve data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initializeViews() {
        edtName = findViewById(R.id.edt_name)
        edtAge = findViewById(R.id.edt_age)
        edtWeight = findViewById(R.id.edt_weight)
        edtHeight = findViewById(R.id.edt_height)
        rgGender = findViewById(R.id.rg_gender)
        rbMale = findViewById(R.id.rb_male)
        rbFemale = findViewById(R.id.rb_female)
        rgDiabetes = findViewById(R.id.rg_diabetes)
        rgHypertension = findViewById(R.id.rg_hypertension)

        findViewById<Button>(R.id.btn_submit).setOnClickListener {
            submitData()
        }
    }

    private fun submitData() {
        val name = edtName.text.toString().trim()
        val age = edtAge.text.toString().trim()
        val weight = edtWeight.text.toString().trim()
        val height = edtHeight.text.toString().trim()

        val diabetes = when (rgDiabetes.checkedRadioButtonId) {
            R.id.rb_diabetes_yes -> "Ya"
            R.id.rb_diabetes_no -> "Tidak"
            else -> "Please select"
        }

        val hypertension = when (rgHypertension.checkedRadioButtonId) {
            R.id.rb_hypertension_yes -> "Ya"
            R.id.rb_hypertension_no -> "Tidak"
            else -> "Please select"
        }

        val gender = when {
            rbMale.isChecked -> "Male"
            rbFemale.isChecked -> "Female"
            else -> "Please select"
        }

        if (name.isEmpty() || age.isEmpty() || weight.isEmpty() || height.isEmpty() ||
            diabetes == "Please select" || hypertension == "Please select" || gender == "Please select"
        ) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val currentUser  = FirebaseAuth.getInstance().currentUser
        if (currentUser  == null || currentUser .uid.isNullOrEmpty()) {
            Toast.makeText(this, "No user is logged in", Toast.LENGTH_SHORT).show()
            return
        }

        val uid = currentUser .uid!!

        val email = currentUser .email!!

        val userData = mapOf(
            "name" to name,
            "age" to age,
            "gender" to gender,
            "weight" to weight,
            "height" to height,
            "diabetes" to diabetes,
            "hypertension" to hypertension,
            "email" to email
        )

        // Simpan data di Firebase Realtime Database
        database.child("users").child(uid).setValue(userData)
            .addOnSuccessListener {
                Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to save data", Toast.LENGTH_SHORT).show()
            }
    }
}