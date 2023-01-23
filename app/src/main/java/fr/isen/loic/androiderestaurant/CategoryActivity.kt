package fr.isen.loic.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.loic.androiderestaurant.databinding.ActivityCategoryBinding
import fr.isen.loic.androiderestaurant.model.Data
import fr.isen.loic.androiderestaurant.model.Item
import org.json.JSONException
import org.json.JSONObject
import java.io.File

class CategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryBinding

    private lateinit var categorie: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        categorie = intent.getStringExtra("category").toString()
        title = categorie

        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title.text = categorie
        binding.toolbar.pannier.setOnClickListener {
            startActivity(Intent(this, PannierActivity::class.java))
        }
        binding.list.layoutManager = LinearLayoutManager(null)
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
                    binding.list.adapter = datas.data?.first { it.name_fr == categorie }?.items?.let { e ->
                        CategoryAdapter(e) {
                            //Toast.makeText(this, "You clicked on ${it.name_fr}", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, DetailActivity::class.java).putExtra("item", it))
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