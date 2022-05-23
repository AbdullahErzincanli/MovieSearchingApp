package com.example.sinemalar.view

import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.example.sinemalar.R
import kotlinx.android.synthetic.main.fragment_splash.*

class SplashFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()

        val typeFace: Typeface = Typeface.createFromAsset(context?.assets,"carbon bl.ttf")
        tv_app_name.typeface = typeFace

        Handler().postDelayed({
            // Start the Intro Activity
            val action = SplashFragmentDirections.actionSplashFragmentToFilmSearchingFragment()
            Navigation.findNavController(view).navigate(action)
        }, 5000)

    }

}