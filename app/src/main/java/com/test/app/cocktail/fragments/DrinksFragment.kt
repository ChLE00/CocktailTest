package com.test.app.cocktail.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.test.app.cocktail.R
import com.test.app.cocktail.activities.MainActivity
import com.test.app.cocktail.adapters.DrinksRecipesAdapter
import com.test.app.cocktail.databinding.FragmentDrinksBinding
import com.test.app.cocktail.models.Drink
import com.test.app.cocktail.network.Status
import com.test.app.cocktail.utils.SharedPrefaceHelper
import com.test.app.cocktail.utils.SharedPrefaceHelper.Companion.ByAlphabet
import com.test.app.cocktail.utils.hideKeyboard
import com.test.app.cocktail.viewModels.DrinkViewModel
import es.dmoral.toasty.Toasty


class DrinksFragment : Fragment() {

    private lateinit var binding: FragmentDrinksBinding

    private lateinit var drinksAdapter: DrinksRecipesAdapter
    private val drinksData = ArrayList<Drink>()

    val drinksviewModel: DrinkViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_drinks,
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


        if (ByAlphabet != false) {
            binding.sByAlpha.setChecked(true)
            SharedPrefaceHelper.removeByName()
            drinksviewModel.drinkAlphabet()
        } else {
            binding.sByName.setChecked(true)
            SharedPrefaceHelper.removeByAlphabet()
            drinksviewModel.drinkName()
        }

        binding.sByName.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, one_isChecked ->
            if (binding.sByAlpha.isChecked) {
                binding.sByAlpha.setChecked(false)
                binding.sByName.setChecked(true)
            }
            SharedPrefaceHelper.removeByAlphabet()
            SharedPrefaceHelper.saveByName(one_isChecked)
            drinksviewModel.drinkName()
        })

        binding.sByAlpha.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, two_isChecked ->
            if (binding.sByName.isChecked) {
                binding.sByName.setChecked(false)
                binding.sByAlpha.setChecked(true)
            }
            SharedPrefaceHelper.removeByName()
            SharedPrefaceHelper.saveByAlphabet(two_isChecked)
            drinksviewModel.drinkAlphabet()
        })

        //Drinks by Name
        drinksviewModel.drinksResponse.observe(
            activity,
            androidx.lifecycle.Observer {
                when (it.status) {
                    Status.LOADING -> {
                        binding.loading.visibility = View.VISIBLE
                        binding.rvDrinks.visibility = View.GONE
                        //binding.txtMore.visibility = View.GONE
                    }
                    Status.SUCCESS -> {
                        binding.loading.visibility = View.GONE
                        Log.d("Response", "" + it.data!!.body()?.drinks!!.get(0)!!.strDrink)

                        if (!it.data!!.body()?.drinks.isNullOrEmpty()) {
                            binding.rvDrinks.visibility = View.VISIBLE
                            Toasty.success(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                            //binding.txtMore.visibility = View.VISIBLE
                            drinksData.clear()
                            for (i in it.data!!.body()?.drinks!!.indices) {
                                drinksData.add(
                                    Drink(
                                        it.data!!.body()!!.drinks?.get(i)!!.strAlcoholic,
                                        it.data!!.body()!!.drinks?.get(i)!!.strDrink,
                                        it.data!!.body()!!.drinks?.get(i)!!.strDrinkThumb,
                                        it.data!!.body()!!.drinks?.get(i)!!.strInstructions
                                    )
                                )

                            }
                            drinksAdapter =
                                DrinksRecipesAdapter(activity, drinksData)
                            binding.rvDrinks.adapter = drinksAdapter
                            drinksAdapter.notifyDataSetChanged()
                        } else {
                            binding.rvDrinks.visibility = View.GONE
                        }
                    }
                    Status.ERROR -> {
                        binding.loading.visibility = View.GONE
                        binding.rvDrinks.visibility = View.GONE
                        //binding.txtMore.visibility = View.GONE
                        Toasty.error(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )
    }

}
