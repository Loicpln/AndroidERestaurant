package fr.isen.loic.androiderestaurant

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import fr.isen.loic.androiderestaurant.databinding.ActivityDetailBinding
import fr.isen.loic.androiderestaurant.model.Plat

class DetailActivity: AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var plat: Plat
    private var quantity: Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        plat = intent.getSerializableExtra("item") as Plat
        title = "Détail"
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPager.adapter = ImageAdapter(this, plat.images)
        binding.name.text = plat.name_fr
        binding.ingredients.text = plat.ingredients?.joinToString(", ") { it.name_fr }

        plat.prices?.forEach { prix ->
            val button = Button(this)
            button.text = "${prix.size} : ${(prix.price * quantity).toString().replace(".", "€ ")}0"
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

    fun changePrice(){
        binding.addToCart.removeAllViews()
        plat.prices?.forEach { prix ->
            val button = Button(this)
            button.text = "${prix.size} : ${(prix.price * quantity).toString().replace(".", "€ ")}0"
            binding.addToCart.addView(button)
        }
    }
}