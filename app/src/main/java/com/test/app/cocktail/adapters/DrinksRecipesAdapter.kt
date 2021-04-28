package com.test.app.cocktail.adapters

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.bumptech.glide.Glide
import com.test.app.cocktail.R
import com.test.app.cocktail.databinding.ItemDrinksBinding
import com.test.app.cocktail.db.FavEntity
import com.test.app.cocktail.db.Localdb
import com.test.app.cocktail.models.Drink
import es.dmoral.toasty.Toasty

class DrinksRecipesAdapter(
    var mContext: Context,
    var data: List<Drink>

) : RecyclerView.Adapter<DrinksRecipesAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemDrinksBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            mContext: Context,
            data: List<Drink>

        ) {

            Glide.with(mContext)
                .load(data[adapterPosition].strDrinkThumb)
                .error(R.drawable.not_available)
                .circleCrop()
                .into(binding.img)
            binding.tvName.text = data[adapterPosition].strDrink
            binding.tvDesc.text = data[adapterPosition].strInstructions
            if (data[adapterPosition].strAlcoholic.equals("Alcoholic")) {
                binding.chk.setChecked(true)
            } else {
                binding.chk.setChecked(false)
            }

            var db =
                Room.databaseBuilder(mContext.applicationContext, Localdb::class.java, "FavoriteDB")
                    .fallbackToDestructiveMigration()
                    .build()
            binding.imgFav.setOnClickListener {
                if (binding.imgFav.isChecked) {
                    binding.imgFav.setChecked(true)
                    Thread {
                        val drinkName = data[adapterPosition].strDrink
                        val instruction = data[adapterPosition].strInstructions
                        val alcholic = data[adapterPosition].strAlcoholic
                        val thumb = data[adapterPosition].strDrinkThumb
                        val fav = FavEntity(
                            drinkName,
                            instruction,
                            alcholic,
                            thumb
                        )
                        db.fav_DAO().saveFav(fav)
                        Handler(Looper.getMainLooper()).post {
                            Toasty.success(
                                mContext,
                                "Drink Successfully Saved in Favorites!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }.start()
                } else {
                    binding.imgFav.setChecked(false)
                    Thread {
                        val drinkName = data[adapterPosition].strDrink
                        val instruction = data[adapterPosition].strInstructions
                        val alcholic = data[adapterPosition].strAlcoholic
                        val thumb = data[adapterPosition].strDrinkThumb
                        val fav = FavEntity(
                            drinkName,
                            instruction,
                            alcholic,
                            thumb
                        )
                        db.fav_DAO().deleteFav(fav)
                        Handler(Looper.getMainLooper()).post {
                            Toasty.success(
                                mContext,
                                "Drink Successfully Deleted From Favorites!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }.start()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): DrinksRecipesAdapter.ViewHolder {

        val v = DataBindingUtil.inflate<ItemDrinksBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_drinks, parent, false
        )
        return DrinksRecipesAdapter.ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: DrinksRecipesAdapter.ViewHolder, p1: Int) {
        holder.bind(mContext, data)
    }

    fun filterList(filteredDrinks: ArrayList<Drink>) {
        this.data = filteredDrinks
        notifyDataSetChanged()
    }

}

