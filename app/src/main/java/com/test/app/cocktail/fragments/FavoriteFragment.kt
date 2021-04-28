package com.test.app.cocktail.fragments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.app.cocktail.R
import com.test.app.cocktail.activities.MainActivity
import com.test.app.cocktail.adapters.FavoriteRecipesAdapter
import com.test.app.cocktail.databinding.FragmentFavoriteBinding
import com.test.app.cocktail.utils.hideKeyboard
import com.test.app.cocktail.viewModels.FavoriteViewModel

class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var mFavoriteViewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_favorite,
            container,
            false
        )

        return binding.root

    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        hideKeyboard(requireActivity())
        val activity = requireActivity() as MainActivity

        val adapter = FavoriteRecipesAdapter()
        val recyclerView = binding.rvFavorites
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.loading.visibility = View.VISIBLE
        mFavoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        mFavoriteViewModel.fetchAllFav.observe(viewLifecycleOwner, Observer { fav ->
            adapter.setData(fav)
            adapter.notifyDataSetChanged()
            Handler().postDelayed(Runnable {
                binding.loading.visibility=View.GONE
            },3000)
        })

    }
}