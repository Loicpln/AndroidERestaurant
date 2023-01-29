package fr.isen.loic.androiderestaurant

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import fr.isen.loic.androiderestaurant.databinding.ActivityPannierBinding
import fr.isen.loic.androiderestaurant.model.Item
import java.io.File

class PannierActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPannierBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pannier)

        binding = ActivityPannierBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title.text = "Pannier"

        refreshPannier()
        reloadLayout()
    }

    private fun reloadLayout() {
        val file = File(this.filesDir, "pannier.json")
        if (file.exists()) {
            val json = file.readText()
            val pannier = Gson().fromJson(json, Array<Item>::class.java)
            binding.list.layoutManager = LinearLayoutManager(null)
            binding.list.adapter = PannierAdapter(pannier) { target ->
                File(this.filesDir, "pannier.json").writeText(Gson().toJson(pannier.filter { it !== target }.toTypedArray()))
                refreshPannier()
                reloadLayout()
            }
        }
    }

    private fun refreshPannier() {
        val file = File(this.filesDir, "pannier.json")
        if (file.exists()) {
            val json = file.readText()
            val pannier = Gson().fromJson(json, Array<Item>::class.java)
            if(pannier.isNotEmpty()) {
                binding.toolbar.pastille.visibility = View.VISIBLE
            }else{
                binding.toolbar.pastille.visibility = View.GONE
            }
            binding.toolbar.pastille.text = pannier.size.toString()
        }
    }

    override fun onResume() {
        super.onResume()
        refreshPannier()
    }
}