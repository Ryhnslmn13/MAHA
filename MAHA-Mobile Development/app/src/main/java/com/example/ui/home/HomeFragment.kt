package com.example.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adapter.ArticleAdapter
import com.example.api.Article
import com.example.mahaapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlin.math.pow

class HomeFragment : Fragment() {

    private lateinit var tvGreeting: TextView
    private lateinit var tvHeight: TextView
    private lateinit var tvWeight: TextView
    private lateinit var tvBMI: TextView
    private lateinit var database: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var articleAdapter: ArticleAdapter
    private val articlesList = mutableListOf<Article>()

    @SuppressLint("CutPasteId", "DefaultLocale")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        tvGreeting = view.findViewById(R.id.tv_greeting)
        tvHeight = view.findViewById(R.id.tv_Height)
        tvWeight = view.findViewById(R.id.tv_Weight)
        tvBMI = view.findViewById(R.id.tv_bmi)

        recyclerView = view.findViewById(R.id.recyclerViewArticles)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val articles = listOf(
            Article(
                "Pesan Terawan agar Masyarakat Terhindar dari Penyakit Diabetes hingga Stroke",
                "Penyakit degeneratif seperti hipertensi, serangan jantung, stroke, kanker, dan diabetes melitus kini semakin banyak diidap, dokter Terawan titip pesan ini.",
                "https://www.liputan6.com/health/read/5794170/pesan-terawan-agar-masyarakat-terhindar-dari-penyakit-diabetes-hingga-stroke",
                "2024-11-19T05:28:18Z",
                "Liputan6.com"
            ),
            Article(
                "Kasus Diabetes pada Anak Meningkat 70 persen Sejak 2010, KPAI: Perlu Tindakan Tegas",
                "Obesitas anak sering kali berujung pada masalah kesehatan jangka panjang, termasuk diabetes tipe 2 dan penyakit jantung.",
                "https://www.liputan6.com/health/read/5809205/kasus-diabetes-pada-anak-meningkat-70-persen-sejak-2010-kpai-perlu-tindakan-tegas",
                "2024-11-27T08:45:05Z",
                "Liputan6.com"
            ),
            Article(
                "Tidak Bisa Jauh dari Makanan Manis? Mungkin DNA Anda Penyebabnya",
                "Para peneliti menemukan bahwa penduduk Greenland yang tidak dapat mencerna sukrosa (gula) sama sekali mengonsumsi lebih sedikit makanan kaya sukrosa.",
                "https://www.liputan6.com/health/read/5792155/tidak-bisa-jauh-dari-makanan-manis-mungkin-dna-anda-penyebabnya",
                "2024-11-17T10:17:10Z",
                "Liputan6.com"
            ),
            Article(
                "Daftar Makanan untuk Mengontrol Tekanan Darah, Cocok Buat Pasien Hipertensi",
                "Ternyata makanan juga berperan dalam mengatur tekanan darah. Berikut beberapa makanan yang cocok untuk orang yang terkena hipertensi, apa saja?",
                "https://health.detik.com/berita-detikhealth/d-7654920/daftar-makanan-untuk-mengontrol-tekanan-darah-cocok-buat-pasien-hipertensi",
                "2024-11-24T22:17:58Z",
                "Detik.com"
            ),
            Article(
                "Bayi Prematur Kerap Alami GERD, Apa Penyebabnya?",
                "GERD berkaitan dengan kondisi saluran cerna bayi prematur yang berbeda dengan bayi pada umumnya.",
                "https://www.liputan6.com/health/read/5799021/bayi-prematur-kerap-alami-gerd-apa-penyebabnya",
                "2024-11-21T10:11:02Z",
                "Liputan6.com"
            ),
            Article(
                "Tren Batu Ginjal di Usia Muda Meningkat, Waspadai Gejala yang Kerap Tak Disadari",
                "Kasus batu ginjal meningkat di kalangan usia muda, terutama pada remaja perempuan. Waspadai gejalanya yang kerap tak disadari.",
                "https://health.detik.com/berita-detikhealth/d-7675172/tren-batu-ginjal-di-usia-muda-meningkat-waspadai-gejala-yang-kerap-tak-disadari",
                "2024-12-07T03:16:04Z",
                "Detik.com"
            ),
            Article(
                "Rajin Konsumsi Cemilan Ini Bantu Cegah Pikun karena Tingkatkan Daya Ingat",
                "Para peneliti di National Institute on Aging menemukan bahwa makan kenari dapat membantu meningkatkan daya ingat pada pasien Alzheimer.",
                "https://www.liputan6.com/health/read/5793216/rajin-konsumsi-cemilan-ini-bantu-cegah-pikun-karena-tingkatkan-daya-ingat",
                "2024-11-18T09:17:46Z",
                "Liputan6.com"
            ),
            Article(
                "Dampak Kolesterol Tinggi, Asam Urat, dan Tensi pada Kesuburan Pria: Apakah Inseminasi Masih Aman?",
                "Kondisi seperti kolesterol tinggi dan gula darah tinggi berisiko mengganggu kesehatan pria, meski inseminasi tetap bisa dilakukan. Menjaga kesehatan tubuh sebelum prosedur sangat disarankan untuk keberhasilan.",
                "https://www.liputan6.com/health/read/5797488/dampak-kolesterol-tinggi-asam-urat-dan-tensi-pada-kesuburan-pria-apakah-inseminasi-masih-aman",
                "2024-11-21T01:16:52Z",
                "Liputan6.com"
            ),
            Article(
                "Kemenkes Prediksi Angka Diabetes di Indonesia Naik hingga 28,5 Juta pada 2045, Deteksi Dini dan Mandiri Amat Penting",
                "Kemenkes Prediksi Angka Diabetes di Indonesia Naik hingga 28,5 Juta pada 2045, Deteksi Dini dan Mandiri Amat Pentingliputan6.com",
                "https://www.l iputan6.com/health/read/5793225/kemenkes-prediksi-angka-diabetes-di-indonesia-naik-hingga-285-juta-pada-2045-deteksi-dini-dan-mandiri-amat-penting",
                "2024-11-18T12:58:28Z",
                "Liputan6.com"
            ),
            Article(
                "Cegah Diabetes pada Anak, IDAI Sarankan Pemerintah Atur Takaran Gula dan Cantumkan pada kemasan Makanan",
                "Prevalensi diabetes pada anak cenderung meningkat, pencantuman takaran gula pada makanan dapat membantu meningkatkan perhatian orangtua terhadap kadar gula dalam makanan anak.",
                "https://www.liputan6.com/health/read/5808775/cegah-diabetes-pada-anak-idai-sarankan-pemerintah-atur-takaran-gula-dan-cantumkan-pada-kemasan-makanan",
                "2024-11-27T00:56:28Z",
                "Liputan6.com"
            ),
            Article(
                "Tragis! Anak Indonesia Kekurangan Vitamin D di Tengah Sinar Matahari Melimpah",
                "Meski kaya sinar matahari, banyak anak Indonesia mengalami kekurangan vitamin D. Kurangnya aktivitas luar ruang dan kebiasaan berpakaian tertutup jadi faktor yang berkontribusi pada defisiensi ini.",
                "https://www.liputan6.com/health/read/5780134/tragis-anak-indonesia-kekurangan-vitamin-d-di-tengah-sinar-matahari-melimpah",
                "2024-11-09T23:16:55Z",
                "Liputan6.com"
            ),
            Article(
                "Eksperimen Nyata, Benarkah Makan Banyak Telur Tidak Menaikkan Kolesterol",
                "Eksperimen 720 telur dalam sebulan menghasilkan data mengejutkan. Mahasiswa Harvard ini membuktikan bahwa konsumsi kolesterol tinggi tidak selalu meningkatkan LDL, dengan diet dan gaya hidup yang tepat.",
                "https://www.fimela.com/health/read/5808393/eksperimen-nyata-benarkah-makan-banyak-telur-tidak-menaikkan-kolesterol",
                "2024-11-26T18:16:13Z",
                "Fimela.com"
            ),
            Article(
                "Menjaga Kualitas Sperma: Berapa Lama Waktu yang Dibutuhkan untuk Meningkatkan Kesehatannya?",
                "Meningkatkan kualitas sperma membutuhkan waktu dan usaha. Pola hidup sehat, olahraga teratur, tidur cukup, serta perawatan hormon berperan penting dalam meningkatkan kesuburan pria.",
                "https://www.liputan6.com/health/read/5799239/menjaga-kualitas-sperma-berapa-lama-waktu-yang-dibutuhkan-untuk-meningkatkan-kesehatannya",
                "2024-11-22T00:17:42Z",
                "Liputan6.com"
            ),
            Article(
                "Luncurkan GLHS, Prof Deby Vinski Kenalkan Teknologi Digital Twins yang Bantu Perawatan Medis Lebih Personal",
                "Teknologi memungkinkan dokter dan pasien terhubung kapan pun meski terpisah jarak dan waktu serta perawatan medis yang lebih personal.",
                "https://www.liputan6.com/health/read/5793936/luncurkan-glhs-prof-deby-vinski-kenalkan-teknologi-digital-twins-yang-bantu-perawatan-medis-lebih-personal",
                "2024-11-19T00:09:19Z",
                "Liputan6.com"
            ),
            Article(
                "Kecombrang dan Kunyit, Rahasia Alami Turunkan Kolesterol Menurut Pakar IPB",
                "Profesor IPB mengungkapkan bahwa kecombrang dan kunyit dapat membantu menurunkan kolesterol, memberikan manfaat kesehatan yang luar biasa dan alami.",
                "https://www.liputan6.com/health/read/5786983/kecombrang-dan-kunyit-rahasia-alami-turunkan-kolesterol-menurut-pakar-ipb",
                "2024-11-14T10:17:13Z",
                "Liputan6.com"
            ),
            Article(
                "Kejar Target Eliminasi TB pada 2030, Menkes Budi Gunadi Ungkap 3 Inisiatif Perangi Tuberkulosis",
                "Guna mengejar target eliminasi TB 2030, pemerintah menyusun tiga inovasi dalam mendorong pemerataan akses pengobatan, peningkatan kesadaran masyarakat, serta pemanfaatan teknologi untuk diagnosis lebih cepat dan akurat.",
                "https://www.liputan6.com/health/read/5783442/kejar-target-eliminasi-tb-pada-2030-menkes-budi-gunadi-ungkap-3-inisiatif-perangi-tuberkulosis",
                "2024-11-12T00:45:41Z",
                "Liputan6.com"
            ),
            Article(
                "5 Rahasia Kulit Sehat ala Artis Korea, Jaga Pola Makan-Minum Kolagen",
                "5 Rahasia Kulit Sehat ala Artis Korea, Jaga Pola Makan-Minum Kolagen.",
                "https://health.detik.com/berita-detikhealth/d-7655291/5-rahasia-kulit-sehat-ala-artis-korea-jaga-pola-makan-minum-kolagen",
                "2024-11-25T05:28:52Z",
                "Detik.com"
            )

        )

        articlesList.addAll(articles)
        articleAdapter = ArticleAdapter(articlesList)
        recyclerView.adapter = articleAdapter

        database = FirebaseDatabase.getInstance("https://maha-app-443810-default-rtdb.asia-southeast1.firebasedatabase.app/").reference

        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            database.child("users").child(currentUser.uid).get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userData = task.result.value as? Map<*, *>
                    if (userData != null) {
                        val fullName = userData["name"] as? String ?: "User "
                        val firstName = fullName.split(" ").firstOrNull() ?: "User "
                        val height = userData["height"] as? String ?: "Tidak diketahui"
                        val weight = userData["weight"] as? String ?: "Tidak diketahui"

                        val bmi = weight.toDouble() / (height.toDouble()/ 100.0).pow(2)

                        tvGreeting.text = getString(R.string.greeting_text, firstName)
                        tvHeight.text = getString(R.string.height_text, height)
                        tvWeight.text = getString(R.string.weight_text, weight)

                        val bmiTextView = view.findViewById<TextView>(R.id.tv_bmi)
                        bmiTextView.text = getString(R.string.bmi_text, String.format("%.2f", bmi))
                        animateTypingText(tvGreeting, getString(R.string.greeting_text, firstName))
                    } else {
                        tvGreeting.text = getString(R.string.greeting_text, "User ")
                        tvHeight.text = getString(R.string.height_text, "Tidak diketahui")
                        tvWeight.text = getString(R.string.weight_text, "Tidak diketahui")

                        animateTypingText(tvGreeting, getString(R.string.greeting_text, "User "))

                    }
                } else {
                    tvGreeting.text = getString(R.string.greeting_text, "User ")
                    tvHeight.text = getString(R.string.height_text, "Tidak diketahui")
                    tvWeight.text = getString(R.string.weight_text, "Tidak diketahui")

                    animateTypingText(tvGreeting, getString(R.string.greeting_text, "User "))
                }
            }
        } else {
            tvGreeting.text = getString(R.string.greeting_text, "User ")
            tvHeight.text = getString(R.string.height_text, "Tidak diketahui")
            tvWeight.text = getString(R.string.weight_text, "Tidak diketahui")

            animateTypingText(tvGreeting, getString(R.string.greeting_text, "User "))
        }

        return view
    }

    private fun animateTypingText(textView: TextView, text: String) {
        val handler = Handler(Looper.getMainLooper())
        var index = 0

        val typingRunnable = object : Runnable {
            override fun run() {
                if (index < text.length) {
                    textView.text = text.substring(0, index + 1)
                    index++
                    handler.postDelayed(this, 100)
                } else {
                    handler.postDelayed({
                        index = 0
                        textView.text = ""
                        handler.post(this)
                    }, 1000)
                }
            }
        }

        handler.post(typingRunnable)
    }
}