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

class HomeAdapterForRecyclerView(val context: Context): RecyclerView.Adapter<HomeAdapterForRecyclerView.HomeFragmentViewHolder>() {

    var images: ArrayList<Drawable>? = null

inner class HomeFragmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    var image_button: ImageView
    init {
        image_button = itemView.findViewById(R.id.button_image_view)
    image_button.setOnClickListener {
        val intent = Intent(context, DetailsHome::class.java)
        intent.putExtra(Constants.DATA_POSITION, bindingAdapterPosition)
        context.startActivity(intent)
    }
    }




}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeFragmentViewHolder {
        images = ArrayList()
        images!!.add(ContextCompat.getDrawable(parent.context, R.drawable.today)!!)
        images!!.add(ContextCompat.getDrawable(parent.context, R.drawable.week)!!)
        images!!.add(ContextCompat.getDrawable(parent.context, R.drawable.today2)!!)
        images!!.add(ContextCompat.getDrawable(parent.context, R.drawable.week2)!!)
        return HomeFragmentViewHolder(LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_for_home_fragment_recycler_view, parent, false))
    }

    override fun onBindViewHolder(holder: HomeFragmentViewHolder, position: Int) {
        holder.image_button.setImageDrawable(images?.get(position))

    }

    override fun getItemCount(): Int {
        return 4
    }
}