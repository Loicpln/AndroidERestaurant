package fr.isen.loic.androiderestaurant

import com.android.volley.Request
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.loic.androiderestaurant.databinding.ActivityCategoryBinding
import fr.isen.loic.androiderestaurant.model.Categorie
import fr.isen.loic.androiderestaurant.model.Data
import fr.isen.loic.androiderestaurant.model.Plat
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.Locale.Category

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

        /*val plat = when (title) {
            "Entrée" -> resources.getStringArray(R.array.entree)
            "Plat" -> resources.getStringArray(R.array.plat)
            else -> resources.getStringArray(R.array.dessert)
        }
        recyclerView.adapter = CategoryAdapter(plat) {
            Toast.makeText(this, "You clicked on $it", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, DetailActivity::class.java).putExtra("item", it))
        }*/
        loadPlatsFromApi()
    }

    private fun loadPlatsFromApi() {
        val jsonObject = JSONObject()
        jsonObject.put("id_shop", "1")
        val request = JsonObjectRequest(Request.Method.POST,
            "http://test.api.catering.bluecodegames.com/menu",
            jsonObject,
            { response ->
                try {
                    val datas = Gson().fromJson(response.toString(), Data::class.java)
                    datas.data.forEach { item ->
                        if (item.name_fr == title) {
                            recyclerView.adapter = CategoryAdapter(item.items) {
                                Toast.makeText(this, "You clicked on $it", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, DetailActivity::class.java).putExtra("item", it))
                            }
                        }
                    }
                } catch (e: JSONException) {
                    Toast.makeText(this, "Error parsing JSON", Toast.LENGTH_SHORT).show()
                }
            },
            {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            })
        Volley.newRequestQueue(this).add(request)
    }
}