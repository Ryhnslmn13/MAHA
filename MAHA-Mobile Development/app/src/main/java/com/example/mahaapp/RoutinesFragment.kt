package com.example.mahaapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar

class RoutinesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var exerciseAdapter: ExerciseAdapter

    private val exerciseList = listOf(

        ExerciseItem("Bersepeda Santai",
            "Bersepeda santai adalah bentuk olahraga kardio yang sangat baik untuk meningkatkan kondisi kardiovaskular tanpa memberikan tekanan berlebih pada sendi. Aktivitas ini membantu menjaga tekanan darah tetap stabil, memperkuat otot kaki, dan meningkatkan fleksibilitas. Bersepeda juga dapat dilakukan di luar ruangan untuk menikmati pemandangan atau di sepeda statis di dalam ruangan.",
            R.drawable.ic_biking),

        ExerciseItem("Jogging Ringan",
            "Jogging ringan adalah latihan kardiovaskular yang sangat baik untuk meningkatkan kesehatan jantung dan memperbaiki fungsi paru-paru. Aktivitas ini juga membantu memperkuat otot-otot tubuh bagian bawah dan meningkatkan kapasitas aerobik. Selain itu, jogging dapat membantu mengurangi stres dan kecemasan, yang berkontribusi pada pengelolaan tekanan darah yang lebih baik.",
            R.drawable.ic_jogging),

        ExerciseItem("Yoga",
            "Yoga adalah latihan fisik dan mental yang berfokus pada pernapasan, kelenturan, dan keseimbangan tubuh. Dengan melakukan yoga secara teratur, seseorang dapat menurunkan tingkat stres, meningkatkan fleksibilitas tubuh, serta memperbaiki postur dan keseimbangan tubuh. Yoga juga sangat bermanfaat bagi penderita hipertensi karena dapat membantu menurunkan tekanan darah dengan cara yang alami.",
            R.drawable.ic_yoga),

        ExerciseItem("Angkat Beban Ringan",
            "Latihan angkat beban ringan adalah bentuk latihan kekuatan yang membantu memperkuat otot-otot tubuh, meningkatkan metabolisme, dan menjaga kesehatan jantung. Dengan menggunakan beban yang tidak terlalu berat, latihan ini sangat cocok bagi penderita hipertensi yang ingin meningkatkan massa otot tanpa membebani sistem kardiovaskular. Latihan ini juga dapat membantu menurunkan berat badan dan menjaga kadar gula darah tetap stabil.",
            R.drawable.weightlifting),
        
        ExerciseItem("Jalan Cepat",
            "Jalan cepat adalah jenis olahraga kardio ringan yang dapat dilakukan kapan saja dan di mana saja. Olahraga ini efektif untuk meningkatkan sirkulasi darah, membakar kalori, dan menurunkan tekanan darah tinggi. Selain itu, jalan cepat juga membantu meningkatkan kekuatan otot kaki dan meningkatkan stamina secara keseluruhan.",
            R.drawable.ic_walking),


        ExerciseItem("Renang",
            "Renang adalah latihan yang sangat efektif untuk menjaga tekanan darah tetap stabil, terutama bagi mereka yang memiliki hipertensi. Olahraga ini melibatkan hampir semua otot tubuh dan merupakan latihan kardiovaskular yang rendah dampak, sehingga aman dilakukan oleh orang dengan masalah sendi atau yang sedang dalam pemulihan. Renang juga sangat baik untuk melatih pernapasan dan meningkatkan stamina secara keseluruhan.",
            R.drawable.ic_swimming)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_routines, container, false)

        val toolbar: MaterialToolbar = view.findViewById(R.id.toolbar)
        toolbar.setNavigationOnClickListener {

        }

        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
        (activity as? AppCompatActivity)?.supportActionBar?.title = getString(R.string.rekomendasi_gym)

        recyclerView = view.findViewById(R.id.recyclerViewRoutines)
        exerciseAdapter = ExerciseAdapter(exerciseList)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = exerciseAdapter

        return view
    }
}
