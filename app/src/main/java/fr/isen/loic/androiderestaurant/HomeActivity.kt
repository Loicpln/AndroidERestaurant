package fr.isen.loic.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import android.widget.Toolbar
import com.google.gson.Gson
import fr.isen.loic.androiderestaurant.databinding.ActivityHomeBinding
import fr.isen.loic.androiderestaurant.model.Item
import java.io.File

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title.text = "Home"
        binding.toolbar.pannier.setOnClickListener {
            startActivity(Intent(this, PannierActivity::class.java))
        }
        refreshPannier()
        addLink(binding.entree)
        addLink(binding.plat)
        addLink(binding.dessert)
    }
    private fun refreshPannier() {
        var file = File(this.filesDir, "pannier.json")
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
    private fun addLink(button: Button) {
        button.setOnClickListener {
            //Toast.makeText(this, "You clicked on ${button.text}", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, CategoryActivity::class.java).putExtra("category", button.text))
        }
    }
    override fun onResume() {
        super.onResume()
        refreshPannier()
    }

    override fun onDestroy() {
        super.onDestroy()
        println("HomeActivity destroyed")
    }
}