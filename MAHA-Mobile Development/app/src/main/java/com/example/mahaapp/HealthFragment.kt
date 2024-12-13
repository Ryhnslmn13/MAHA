package com.example.mahaapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.app.AppCompatActivity
import com.example.adapter.FoodAdapter
import com.example.adapter.FoodItem
import com.google.android.material.appbar.MaterialToolbar

class HealthFragment : Fragment() {

    private lateinit var foodList: List<FoodItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_health, container, false)

        // Inisialisasi Toolbar
        val toolbar: MaterialToolbar = view.findViewById(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            // Aksi tombol navigasi (jika diperlukan)
        }

        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
        (activity as? AppCompatActivity)?.supportActionBar?.title = getString(R.string.rekomendasi_makanan_sehat)

        // Set data makanan
        foodList = listOf(
            FoodItem("Pisang", "Buah yang kaya kalium untuk membantu menurunkan tekanan darah.", R.drawable.ic_banana),
            FoodItem("Brokoli", "Sayuran kaya magnesium untuk membantu menurunkan tekanan darah.", R.drawable.ic_brocoli),
            FoodItem("Salmon", "Ikan berlemak yang mengandung omega-3 untuk kesehatan jantung.", R.drawable.ic_salmon),
            FoodItem("Almond", "Kacang kaya lemak sehat dan magnesium.", R.drawable.ic_almond),
            FoodItem("Bayam", "Sayuran kaya kalium dan magnesium yang membantu menurunkan tekanan darah.", R.drawable.ic_spinach),
            FoodItem("Tomat", "Mengandung kalium yang tinggi dan bermanfaat untuk menurunkan tekanan darah.", R.drawable.ic_tomato),
            FoodItem("Oatmeal", "Sumber serat tinggi yang baik untuk menurunkan tekanan darah.", R.drawable.ic_oatmeal),
            FoodItem("Jeruk", "Kaya vitamin C dan kalium yang membantu kesehatan jantung.", R.drawable.ic_orange),
            FoodItem("Susu rendah lemak", "Mengandung kalsium dan vitamin D yang penting untuk kesehatan jantung.", R.drawable.ic_low_fat_milk)
        )

        // Inisialisasi RecyclerView
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewHealth)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = FoodAdapter(foodList)

        return view
    }
}
