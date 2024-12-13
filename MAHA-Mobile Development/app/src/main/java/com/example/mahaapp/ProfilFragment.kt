package com.example.mahaapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.data.User
import com.example.mahaapp.R
import com.example.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfilFragment : Fragment() {

    private lateinit var tvName: TextView
    private lateinit var tvAge: TextView
    private lateinit var tvHeight: TextView
    private lateinit var tvWeight: TextView
    private lateinit var tvHealthCondition: TextView
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_profil, container, false)

        // Inisialisasi tampilan
        tvName = rootView.findViewById(R.id.tvName)
        tvAge = rootView.findViewById(R.id.tvAge)
        tvHeight = rootView.findViewById(R.id.tvHeight)
        tvWeight = rootView.findViewById(R.id.tvWeight)
        tvHealthCondition = rootView.findViewById(R.id.tvHealthCondition)

        // Mendapatkan referensi ke Firebase dengan URL lengkap
        database = FirebaseDatabase.getInstance("https://maha-app-443810-default-rtdb.asia-southeast1.firebasedatabase.app")
            .getReference("users")

        // Mendapatkan uid dari FirebaseAuth
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid != null) {
            getUserData(uid)
        } else {
            Toast.makeText(activity, "User   not logged in", Toast.LENGTH_SHORT).show()
        }

        // Logout
        val logoutImageView: ImageView = rootView.findViewById(R.id.logoutImageView)
        logoutImageView.setOnClickListener {
            val sharedPreferences = activity?.getSharedPreferences("User  Prefs", Context.MODE_PRIVATE)
            sharedPreferences?.edit()?.clear()?.apply()

            val intent = Intent(activity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)

            activity?.finish()
        }

        return rootView
    }

    private fun getUserData(uid: String) {
        database.child(uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val user = snapshot.getValue(User::class.java)
                    user?.let {
                        // Tampilkan data pengguna di profil
                        tvName.text = it.name
                        tvAge.text = "${it.age} Tahun"
                        tvHeight.text = "${it.height} cm"
                        tvWeight.text = "${it.weight} kg"
                        tvHealthCondition.text = "Diabetes: ${it.diabetes}, Hypertension: ${it.hypertension}"
                    }
                } else {
                    // Data pengguna tidak tersimpan di Firebase Realtime Database
                    Toast.makeText(activity, "Data pengguna tidak tersimpan", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Kesalahan saat mengambil data dari Firebase Realtime Database
                Toast.makeText(activity, "Kesalahan saat mengambil data", Toast.LENGTH_SHORT).show()
            }
        })
    }
}