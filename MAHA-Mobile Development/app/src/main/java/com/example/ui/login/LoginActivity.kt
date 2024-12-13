package com.example.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mahaapp.databinding.ActivityLoginBinding
import com.example.ui.signup.SignupActivity
import com.example.mahaapp.MainActivity
import com.example.ui.datadiri.DataDiriActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://maha-app-443810-default-rtdb.asia-southeast1.firebasedatabase.app/").reference

        setupAnimations()

        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            handleLogin(email, password)
        }

        binding.tvNewSignup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupAnimations() {
        binding.imgView.alpha = 0f
        binding.imgView.animate()
            .alpha(1f)
            .setDuration(1000)
            .start()

        binding.cardViewContent.translationY = 300f
        binding.cardViewContent.alpha = 0f
        binding.cardViewContent.animate()
            .translationY(0f)
            .alpha(1f)
            .setDuration(1000)
            .start()

        binding.copyrightTextView.translationY = 100f
        binding.copyrightTextView.alpha = 0f
        binding.copyrightTextView.animate()
            .translationY(0f)
            .alpha(1f)
            .setDuration(1000)
            .setStartDelay(500)
            .start()
    }



    private fun handleLogin(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        } else if (isValidEmail(email) && isValidPassword(password)) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val sharedPreferences = getSharedPreferences("User   Prefs", Context.MODE_PRIVATE)
                        sharedPreferences.edit()
                            .putString("userEmail", email)
                            .apply()

                        val currentUser = FirebaseAuth.getInstance().currentUser
                        if (currentUser != null && currentUser.uid != null) {
                            database.child("users").child(currentUser.uid).get().addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val userData = task.result.value as? Map<String, Any>
                                    if (userData != null) {
                                        val intent = Intent(this, MainActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    } else {
                                        val intent = Intent(this, DataDiriActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    }
                                } else {
                                    Toast.makeText(this, "Failed to retrieve data", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } else {
                            Toast.makeText(this, "No user is logged in", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "Authentication Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Invalid Email or Password", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }
}