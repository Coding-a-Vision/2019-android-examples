package com.migreat.giphy

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.migreat.giphy.model.Gif

class GifsAdapter : ListAdapter<Gif, GifsViewHolder>(GifsDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.listitem_gif, parent, false)
        return GifsViewHolder(view)
    }

    override fun onBindViewHolder(holder: GifsViewHolder, position: Int) {
        val gif = getItem(position)
        gif.let {
            Glide
                .with(holder.gifImage.context)
                .load(gif.preview.url)
                .fitCenter()
                .into(holder.gifImage)
        }

        holder.gifImage.setOnClickListener {
            GifDetailActivity.openGifDetailActivity(holder.gifImage.context as Activity, gif.id)
        }
    }
}

class GifsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val gifImage = itemView.findViewById<ImageView>(R.id.gif_view)
}

class GifsDiffUtil : DiffUtil.ItemCallback<Gif>() {
    override fun areItemsTheSame(oldItem: Gif, newItem: Gif): Boolean {
        return oldItem.original.url == newItem.original.url
    }

    override fun areContentsTheSame(oldItem: Gif, newItem: Gif): Boolean {
        return oldItem == newItem
    }

}
