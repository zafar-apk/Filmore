package com.kangaroo.filmore.Utils

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.kangaroo.filmore.R
import com.kangaroo.filmore.Views.ui.details.DetailsHome

class HomeAdapterForRecyclerView(val context: Context, val mediaType: String) :
    RecyclerView.Adapter<HomeAdapterForRecyclerView.HomeFragmentViewHolder>() {

    var images: ArrayList<Drawable>? = null

    inner class HomeFragmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image_button: ImageView = itemView.findViewById(R.id.button_image_view)

        init {
            image_button.setOnClickListener {
                val intent = Intent(context, DetailsHome::class.java)
                intent.putExtra(Constants.DATA_POSITION, bindingAdapterPosition)
                intent.putExtra(Constants.DATA_MEDIA_TYPE, mediaType)
                context.startActivity(intent)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeFragmentViewHolder {
        images = ArrayList()
        ContextCompat.getDrawable(parent.context, R.drawable.today)?.let { images?.add(it) }
        ContextCompat.getDrawable(parent.context, R.drawable.week)?.let { images?.add(it) }
        return HomeFragmentViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_for_home_fragment_recycler_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HomeFragmentViewHolder, position: Int) {
        holder.image_button.setImageDrawable(images?.get(position))

    }

    override fun getItemCount(): Int {
        return 2
    }
}