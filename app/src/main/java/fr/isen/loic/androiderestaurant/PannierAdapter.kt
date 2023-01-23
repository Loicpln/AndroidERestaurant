package fr.isen.loic.androiderestaurant

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.loic.androiderestaurant.model.Item

class PannierAdapter(private val list: Array<Item>, private val onClick: (Int) -> Unit) : RecyclerView.Adapter<PannierAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val image: ImageView? = view.findViewById(R.id.image)
        private val name: TextView? = view.findViewById(R.id.name)
        private val price: TextView? = view.findViewById(R.id.price)
        private val suppress: Button? = view.findViewById(R.id.suppress)
        private val loading: ProgressBar? = view.findViewById(R.id.loading)

        fun bind(elem: Item, onClick: (Int) -> Unit) {
            Thread {
                var i = 0
                while (i < 100) {
                    i += 5
                    loading?.progress = i
                    Thread.sleep(10)
                }
                loading?.post {
                    loading?.visibility = View.GONE
                    setImage(elem)
                    setTitle(elem)
                    setPrice(elem)
                    suppress?.setOnClickListener {
                        onClick(elem.id)
                    }
                }
            }.start()
        }
        private fun setImage(elem: Item) {
            if(elem.image.isNotEmpty()) {
                Picasso.get().load(elem.image).resize(150,150).centerCrop().into(image)
            }
        }
        private fun setTitle(elem: Item) {
            name?.text = elem.name
        }
        private fun setPrice(elem: Item) {
            price?.text = (elem.price * elem.quantity).toString().replace(".","€ ")+ "0"
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_pannier, parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (list[position] != null) {
            holder.bind(list[position], onClick)
        }
    }

}