package examples.android2019.network.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import examples.android2019.network.R
import examples.android2019.network.domain.Gif

class MainAdapter : ListAdapter<Gif, GifViewHolder>(GifDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.screen_main_gifitem, parent, false)
        return GifViewHolder(view)
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        val category = getItem(position)
        category.let {
            holder.gifTitle.text = it.name
            holder.gifTitle.setOnClickListener {
                Toast.makeText(
                    holder.itemView.context,
                    "clicked gif: ${holder.gifTitle.text}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}

class GifViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val gifTitle = itemView.findViewById<View>(R.id.category_name) as TextView

}

class GifDiffCallback : DiffUtil.ItemCallback<Gif>() {
    override fun areItemsTheSame(oldItem: Gif, newItem: Gif): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Gif, newItem: Gif): Boolean {
        return oldItem == newItem
    }
}
