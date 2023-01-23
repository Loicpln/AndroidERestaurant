package fr.isen.loic.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import fr.isen.loic.androiderestaurant.databinding.ActivityPannierBinding
import fr.isen.loic.androiderestaurant.model.Item
import java.io.File

class PannierActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPannierBinding
    private lateinit var pannier: Array<Item>
    private lateinit var file: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pannier)

        title = "Pannier"
        binding = ActivityPannierBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title.text = "Pannier"
        file = File(this.filesDir, "pannier.json")



        if (file.exists()) {
            val json = file.readText()
            println(json)
            pannier = Gson().fromJson(json, Array<Item>::class.java)
            binding.list.layoutManager = LinearLayoutManager(null)
            reload()
        }
    }

    private fun reload() {
        binding.list.layoutManager = LinearLayoutManager(null)
        binding.list.adapter = PannierAdapter(pannier) { id ->
            pannier = pannier.filter { it.id != id }.toTypedArray()
            File(this.filesDir, "pannier.json").writeText(Gson().toJson(pannier))
            reload()
        }
    }
}