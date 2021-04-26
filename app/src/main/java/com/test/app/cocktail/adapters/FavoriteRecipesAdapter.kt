import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.app.cocktail.R
import com.test.app.cocktail.databinding.ItemDrinksBinding
import com.test.app.cocktail.db.FavEntity

class FavoriteRecipesAdapter() : RecyclerView.Adapter<FavoriteRecipesAdapter.ViewHolder>() {
    private var favList = emptyList<FavEntity>()

    class ViewHolder(val binding: ItemDrinksBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            mContext: Context,
            favList: List<FavEntity>

        ) {

            Glide.with(mContext)
                .load(favList[0].fav_thumb)
                .error(R.drawable.not_available)
                .circleCrop()
                .into(binding.img)
            binding.tvName.text = favList[0].fav_name
            binding.tvDesc.text = favList[0].fav_desc
            if (favList[0].fav_alcohlic.equals("Alcoholic")) {
                binding.chk.setChecked(true)
            } else {
                binding.chk.setChecked(false)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): FavoriteRecipesAdapter.ViewHolder {

        val v = DataBindingUtil.inflate<ItemDrinksBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_drinks, parent, false
        )
        return FavoriteRecipesAdapter.ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return favList.size
    }


    override fun onBindViewHolder(holder: FavoriteRecipesAdapter.ViewHolder, p1: Int) {

    }


    fun setData(favList: List<FavEntity>) {
        this.favList = favList
        notifyDataSetChanged()
    }
}