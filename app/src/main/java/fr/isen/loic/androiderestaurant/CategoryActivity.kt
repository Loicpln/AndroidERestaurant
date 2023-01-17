package fr.isen.loic.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.isen.loic.androiderestaurant.databinding.ActivityCategoryBinding

class CategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryBinding
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        title = intent.getStringExtra("category").toString()
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.list
        recyclerView.layoutManager = LinearLayoutManager(null)

        /*val request = JsonObjectRequest("http://test.api.catering.bluecodegames.com/menu", {
            recyclerView.adapter = CategoryAdapter(it.getJSONObject("data").getJSONArray(intent.getStringExtra("category").toString()))
        },{
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        })

        Volley.newRequestQueue(this).add(request)*/
        when (title) {
            "Entrée" -> recyclerView.adapter = CategoryAdapter(resources.getStringArray(R.array.entree))
            "Plat" -> recyclerView.adapter = CategoryAdapter(resources.getStringArray(R.array.plat))
            "Dessert" -> recyclerView.adapter = CategoryAdapter(resources.getStringArray(R.array.dessert))
            else -> null
        }
    }
}