package com.example.ui.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mahaapp.databinding.ActivitySignupBinding
import com.example.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()


        binding.btnSignup.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val phoneNumber = binding.edtNohp.text.toString()
            val password = binding.edtPassword.text.toString()
            val confirmPassword = binding.edtConfirmPassword.text.toString()

            handleSignup(email, phoneNumber, password, confirmPassword)
        }

        binding.tvHaveAccount.setOnClickListener {
            finish()
        }
    }

    private fun handleSignup(email: String, phoneNumber: String, password: String, confirmPassword: String) {
        if (email.isEmpty() || phoneNumber.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        } else if (password != confirmPassword) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
        } else if (!isValidPhoneNumber(phoneNumber)) {
            Toast.makeText(this, "Invalid phone number", Toast.LENGTH_SHORT).show()
        } else if (isValidEmail(email) && isValidPassword(password)) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Signup Successful", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Registration Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Invalid Email or Password", Toast.LENGTH_SHORT).show()
        }
    }



    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPhoneNumber(phoneNumber: String): Boolean {
        return phoneNumber.length in 10..15 && phoneNumber.all { it.isDigit() }
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 8
    }
}