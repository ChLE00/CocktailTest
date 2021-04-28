package com.test.app.cocktail.adapters

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.bumptech.glide.Glide
import com.test.app.cocktail.R
import com.test.app.cocktail.db.FavEntity
import com.test.app.cocktail.db.Localdb
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.item_drinks.view.*

class FavoriteRecipesAdapter : RecyclerView.Adapter<FavoriteRecipesAdapter.MyViewHolder>() {

    private var favList = emptyList<FavEntity>()
    lateinit var mView: View

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_drinks, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return favList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val Item = favList[position]
        Glide.with(holder.itemView.context)
            .load(Item.fav_thumb)
            .error(R.drawable.not_available)
            .circleCrop()
            .into(holder.itemView.img)
        holder.itemView.tv_name.text = Item.fav_name
        holder.itemView.tv_desc.text = Item.fav_desc
        if (Item.fav_alcohlic.equals("Alcoholic")) {
            holder.itemView.chk.setChecked(true)
        } else {
            holder.itemView.chk.setChecked(false)
        }

        if (!holder.itemView.img_fav.isChecked) {
            holder.itemView.img_fav.setChecked(true)
        }

        var db =
            Room.databaseBuilder(
                holder.itemView.context.applicationContext,
                Localdb::class.java,
                "FavoriteDB"
            )
                .fallbackToDestructiveMigration()
                .build()

        holder.itemView.img_fav.setOnClickListener {
            holder.itemView.img_fav.setChecked(false)
            Thread {
                val drinkName = Item.fav_name
                val instruction = Item.fav_desc
                val alcholic = Item.fav_alcohlic
                val thumb = Item.fav_thumb
                val fav = FavEntity(
                    drinkName,
                    instruction,
                    alcholic,
                    thumb
                )
                db.fav_DAO().deleteFav(fav)
                Handler(Looper.getMainLooper()).post {
                    Toasty.success(
                        holder.itemView.context,
                        "Drink Successfully Deleted From Favorites!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }.start()
        }
    }

    fun setData(fav: List<FavEntity>) {
        this.favList = fav
        notifyDataSetChanged()
    }
}