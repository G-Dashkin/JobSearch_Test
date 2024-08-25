package com.example.jobsearch_test.favorites.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.example.favorites.R
import com.example.favorites.databinding.FragmentFavoritesBinding
import com.example.jobsearch_test.core.navigation.Router
import com.example.jobsearch_test.favorites.di.DaggerFavoritesComponent
import com.example.jobsearch_test.favorites.di.FavoritesFeatureDepsProvider
import javax.inject.Inject

class FavoritesFragment: Fragment(R.layout.fragment_favorites) {

    companion object {
        fun getInstance(): FavoritesFragment = FavoritesFragment()
    }

    private lateinit var binding: FragmentFavoritesBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val startComponent = DaggerFavoritesComponent
            .builder()
            .addDeps(FavoritesFeatureDepsProvider.deps)
            .build()
        startComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoritesBinding.bind(view)
    }
}