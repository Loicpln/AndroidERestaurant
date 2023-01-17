package fr.isen.loic.androiderestaurant

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class CategoryAdapter(private val list: Array<String>) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val cellName: TextView? = view.findViewById(R.id.item)
        fun bind(item: String) {
            cellName?.text = item
            cellName?.setOnClickListener {
                Toast.makeText(it.context, "You clicked on $item", Toast.LENGTH_SHORT).show()
                startActivity(it.context, Intent(it.context, DetailActivity::class.java).putExtra("item", item), null)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

}
