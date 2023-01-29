package fr.isen.loic.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import fr.isen.loic.androiderestaurant.databinding.ActivityDetailBinding
import fr.isen.loic.androiderestaurant.model.Item
import fr.isen.loic.androiderestaurant.model.Plat
import java.io.File

class DetailActivity: AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var plat: Plat
    private var quantity: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        plat = intent.getSerializableExtra("item") as Plat
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title.text = "Détail"
        binding.toolbar.pannier.setOnClickListener {
            startActivity(Intent(this, PannierActivity::class.java))
        }
        refreshPannier()
        binding.viewPager.adapter = ImageAdapter(this, plat.images)
        binding.name.text = plat.name_fr
        binding.ingredients.text = plat.ingredients.joinToString(", ") { it.name_fr }

        plat.prices.forEach { price ->
            val button = Button(this)
            button.id = price.id
            button.text = "${price.size} : ${(price.price * quantity).toString().replace(".", "€ ")}0"
            button.setOnClickListener {
                addInJson(price.price)
                refreshPannier()
                Snackbar.make(binding.root, "Ajouté au pannier", Snackbar.LENGTH_SHORT).show()
            }
            binding.addToCart.addView(button)
        }
        binding.minus.setOnClickListener {
            if (binding.quantity.text.toString().toInt() > 1) {
                quantity--
                changePrice()
                binding.quantity.text = quantity.toString()
            }
        }
        binding.plus.setOnClickListener {
            quantity++
            changePrice()
            binding.quantity.text = quantity.toString()
        }
    }

    private fun addInJson(price: Float) {
        val file = File(this.filesDir, "pannier.json")
        if (!file.exists()) {
            file.createNewFile()
            file.writeText("[{ \"id\": ${plat.id}, \"name\": \"${plat.name_fr}\", \"quantity\": $quantity, \"image\": \"${plat.images[0]}\", \"price\": $price }]")
        }else {
            val json = file.readText()
            if(json == "[]") {
                file.writeText("[{ \"id\": ${plat.id}, \"name\": \"${plat.name_fr}\", \"quantity\": $quantity, \"image\": \"${plat.images[0]}\", \"price\": $price }]")
            }else {
                file.writeText(json.substring(0, json.length - 1) + ", { \"id\": ${plat.id}, \"name\": \"${plat.name_fr}\", \"quantity\": $quantity, \"image\": \"${plat.images[0]}\", \"price\": $price }]")
            }
        }
    }

    private fun changePrice(){
        plat.prices.forEach {
            findViewById<Button>(it.id).text = "${it.size} : ${(it.price * quantity).toString().replace(".", "€ ")}0"
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
        }else{
            binding.toolbar.pastille.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        refreshPannier()
    }
}