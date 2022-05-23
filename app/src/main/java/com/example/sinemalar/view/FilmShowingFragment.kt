package com.example.sinemalar.view

import android.annotation.SuppressLint
import android.app.ActionBar
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.bumptech.glide.Glide
import com.example.sinemalar.R
import kotlinx.android.synthetic.main.fragment_film_showing.*
import kotlinx.android.synthetic.main.item_film.view.*


class FilmShowingFragment : Fragment() {
//private lateinit var viewModel: FilmShowingViewModel
private var filmUuid = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_film_showing, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //// actionbar renk ayarlamaları
        val colorDrawable = ColorDrawable(Color.parseColor("#590D22"))//ee6c4d  335c67  590D22
        (activity as AppCompatActivity).supportActionBar?.setBackgroundDrawable(colorDrawable)
        /// UI elemanlarının tanımlaması
        val ivShowFilmPoster : ImageView = view.findViewById(R.id.ivShowFilmPoster)
        val tvShowFilmGenre : TextView = view.findViewById(R.id.tvShowFilmGenre)
        val tvShowFilmDesc : TextView = view.findViewById(R.id.tvShowFilmDesc)
        val tvShowFilmImbd : TextView = view.findViewById(R.id.tvShowFilmImbd)
        val tvShowFilmAwards: TextView = view.findViewById(R.id.tvShowFilmAwards)
        val tvShowFilmActors: TextView = view.findViewById(R.id.tvShowFilmActors)

// fragmentler arasında yollanan veri bundllarının uygun değişkenlere çekilmesi
        arguments?.let {
            val filmName = FilmShowingFragmentArgs.fromBundle(it).filmName
            val filmGenre = FilmShowingFragmentArgs.fromBundle(it).filmGenre
            val filmPoster = FilmShowingFragmentArgs.fromBundle(it).filmPoster
            val filmPlot = FilmShowingFragmentArgs.fromBundle(it).filmPlot
            val filmImdb = FilmShowingFragmentArgs.fromBundle(it).filmImdb
            val filmAwards = FilmShowingFragmentArgs.fromBundle(it).filmAwards
            val filmActors = FilmShowingFragmentArgs.fromBundle(it).filmActors


            (activity as AppCompatActivity).supportActionBar?.title = filmName // film adının actionbar a verilmesi

            tvShowFilmGenre.text = filmGenre
            tvShowFilmDesc.text = filmPlot
            tvShowFilmImbd.text = "Imdb: $filmImdb"
            tvShowFilmAwards.text = filmAwards
            tvShowFilmActors.text = filmActors
            //filmin posterinin url üzerinden alınarak gösterilmesi
            Glide.with(this).load(filmPoster).into(ivShowFilmPoster)
        }

    }


}