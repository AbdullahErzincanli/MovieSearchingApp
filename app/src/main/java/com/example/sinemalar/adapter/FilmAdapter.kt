package com.example.sinemalar.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sinemalar.R
import com.example.sinemalar.databinding.ItemFilmBinding
import com.example.sinemalar.view.FilmSearchingFragmentDirections
import com.weatherapp.models.OmdbApiData
import kotlinx.android.synthetic.main.item_film.view.*


//class FilmAdapter(val filmNameList: ArrayList<String>,val filmPlotList: ArrayList<String>, val filmUrlList: ArrayList<String>):RecyclerView.Adapter<FilmAdapter.FilmViewHolder>() {
class FilmAdapter(val film: ArrayList<OmdbApiData>):RecyclerView.Adapter<FilmAdapter.FilmViewHolder>() {
    class FilmViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.item_film,parent,false)
        return FilmViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        // item_film layoutundaki uygun eşyalara uygun değişkenlerin atanması
        holder.itemView.tvItemFilmName.text = film[position].title
        holder.itemView.tvItemFilmDesc.text = film[position].plot
        Glide.with(holder.itemView).load(film[position].poster).into(holder.itemView.ivItemFilmPoster)
        // film search ekranındaki gösterilen itemler ın herhangibirisine tıklanması durumunda
        holder.itemView.setOnClickListener{
            val action = FilmSearchingFragmentDirections.actionFilmSearchingFragmentToFilmShowingFragment()
            action.filmName = film[position].title
            action.filmGenre = film[position].genre
            action.filmPoster = film[position].poster
            action.filmPlot = film[position].plot
            action.filmImdb = film[position].imdbRating
            action.filmAwards = film[position].awards
            action.filmActors = film[position].actors
            Navigation.findNavController(it).navigate(action)
        }

    }

    override fun getItemCount(): Int {
        return film.size
    }

}


