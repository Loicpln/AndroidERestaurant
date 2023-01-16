package fr.isen.loic.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    fun onButtonSelected(view: View) {
        val text = when (view.id) {
            R.id.entree -> "Entrée"
            R.id.plat -> "Plat"
            R.id.dessert -> "Dessert"
            else -> "Unknown"
        }
        startActivity(Intent(this, CategoryActivity::class.java).putExtra("category", text))
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(applicationContext, text, duration)
        toast.show()
    }
}