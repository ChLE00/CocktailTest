package com.test.app.cocktail.fragments

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
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

    private var CHANNEL_ID = "SofIt"

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

        createNotification()
        val notifyLayout = RemoteViews(activity.packageName, R.layout.item_drinks)
        var builder:NotificationCompat.Builder=NotificationCompat.Builder(activity,CHANNEL_ID)
            .setContentTitle("Sofit Notify")
            .setSmallIcon(R.drawable.ic_bell)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(notifyLayout)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        //Tried but not worked with alarm Manager & by clicking on notification to open home fragment we can call pending intent.(Not worked with alarm manager as much)
        binding.sByName.setOnClickListener {
            with(NotificationManagerCompat.from(activity)){
                notify(0,builder.build())
            }
        }

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
                        if (!it.data!!.body()?.drinks.isNullOrEmpty()) {
                            binding.rvDrinks.visibility = View.VISIBLE
                            Toasty.success(activity, it.message, Toast.LENGTH_SHORT).show()
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
                        Toasty.error(activity, it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )

        binding.edSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun afterTextChanged(editable: Editable) {
                //after the change calling the method and passing the search input
                filter(editable.toString())


            }
        })

    }

    private fun filter(text: String) {
        val filteredNames = ArrayList<Drink>()
        drinksData.filterTo(filteredNames) {
            it.strDrink.toLowerCase().contains(text.toLowerCase())
        }
        drinksAdapter!!.filterList(filteredNames)
    }

    fun createNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Favorite Drink Notification"
            val desc = "How to make drink description?"
            val importance: Int = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = desc
            }
            val notificationManager: NotificationManager =
                activity?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}
