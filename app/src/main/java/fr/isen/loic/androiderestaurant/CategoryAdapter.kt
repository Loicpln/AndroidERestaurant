package fr.isen.loic.androiderestaurant

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.loic.androiderestaurant.databinding.ActivityDetailBinding
import fr.isen.loic.androiderestaurant.databinding.ActivityHomeBinding
import fr.isen.loic.androiderestaurant.model.Plat
class CategoryAdapter(private val list: Array<Plat>, private val onClick: (Plat) -> Unit) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val image: ImageView? = view.findViewById(R.id.image)
        private val title: TextView? = view.findViewById(R.id.item)
        private val prices: TableLayout? = view.findViewById(R.id.prices)
        private val loading: ProgressBar? = view.findViewById(R.id.loading)


         fun bind(elem: Plat, onClick: (Plat) -> Unit) {
             Thread {
                 var i = 0
                 while (i < 100) {
                     i += 5
                     loading?.progress = i
                     Thread.sleep(10)
                 }
                 loading?.post {
                     loading?.visibility = View.GONE
                     setImages(elem)
                     setTitle(elem)
                     setPrices(elem)
                     itemView.setOnClickListener {
                         onClick(elem)
                     }
                 }
             }.start()
        }
        private fun setImages(elem: Plat) {
            if(elem.images[0].isNotEmpty()) {
                Picasso.get().load(elem.images[0]).resize(150,150).centerCrop().into(image)
            }
        }
        private fun setTitle(elem: Plat) {
            title?.text = elem.name_fr
        }
        private fun setPrices(elem: Plat) {
            elem.prices.forEach { price ->
                val row = TableRow(prices?.context)

                val size = TextView(prices?.context)
                size.textSize = 12f
                size.text = price.size+" : "
                row.addView(size)

                val prix = TextView(prices?.context)
                prix.gravity = Gravity.END
                prix.textSize = 12f
                prix.text = price.price.toString().replace(".", "€ ") + "0"
                row.addView(prix)

                prices?.addView(row)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_item, parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], onClick)
    }



}
