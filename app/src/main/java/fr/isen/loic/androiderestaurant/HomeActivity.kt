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
        title = "Home"
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title.text = "Home"
        binding.toolbar.pannier.setOnClickListener {
            startActivity(Intent(this, PannierActivity::class.java))
        }
        addLink(binding.entree)
        addLink(binding.plat)
        addLink(binding.dessert)
    }

    private fun addLink(button: Button) {
        button.setOnClickListener {
            //Toast.makeText(this, "You clicked on ${button.text}", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, CategoryActivity::class.java).putExtra("category", button.text))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        println("HomeActivity destroyed")
    }
}