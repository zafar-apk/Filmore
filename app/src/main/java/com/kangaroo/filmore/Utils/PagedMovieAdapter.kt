package com.kangaroo.filmore.Utils

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kangaroo.filmore.Models.OneMovie
import com.kangaroo.filmore.R
import com.kangaroo.filmore.Views.ui.discover.DiscoverFragment
import com.squareup.picasso.Picasso

class PagedMovieAdapter(
    val onItemClickListener: OnItemClickListener
) :
    PagingDataAdapter<OneMovie, PagedMovieAdapter.MovieViewHolder>(object :
        DiffUtil.ItemCallback<OneMovie>() {
        override fun areItemsTheSame(oldItem: OneMovie, newItem: OneMovie) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: OneMovie, newItem: OneMovie) = oldItem == newItem
    }) {

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var moviePoster: ImageView
        var title: TextView
        var release: TextView
        var originalTitle: TextView

        init {

            itemView.setOnClickListener(this)
            moviePoster = itemView.findViewById(R.id.poster_image_view)
            title = itemView.findViewById(R.id.title_text_view)
            release = itemView.findViewById(R.id.release_text_view)
            originalTitle = itemView.findViewById(R.id.original_title_text_view)
        }

        override fun onClick(p0: View?) {
            getItem(bindingAdapterPosition)?.let { onItemClickListener.onItemClick(it) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        if (getItem(position)?.first_air_date == null) {
            holder.release.text = "Релиз:" + getItem(position)?.release_date
            holder.title.text = getItem(position)?.title
            holder.originalTitle.text = getItem(position)?.original_title
        } else {
            holder.release.text = "Дата первого показа:" + getItem(position)?.first_air_date
            holder.title.text = getItem(position)?.name
            holder.originalTitle.text = getItem(position)?.original_name
        }

        Picasso.get().load(
            "https://image.tmdb.org/t/p/w500/" +
                    getItem(position)?.poster_path
        ).into(holder.moviePoster)
    }


}