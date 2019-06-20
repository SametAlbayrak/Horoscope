package com.steelsoftware.horoscope.ui.main.categories

import android.content.Context
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.steelsoftware.horoscope.R
import com.steelsoftware.horoscope.ui.uimodels.Category
import com.squareup.picasso.Picasso
import com.steelsoftware.horoscope.utilities.getMenuIndexColor


/**
 * Created by ansh on 01/03/18.
 */
class CategoriesAdapter(private val context: Context?, private val list: ArrayList<Category>,
                        private var listener: CategoriesAdapter.OnItemClickListener)
    : androidx.recyclerview.widget.RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.category_list_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: CategoriesAdapter.ViewHolder, position: Int) {
        holder.bindItems(list[position], listener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickListener {
        fun onItemClick(category: Category)
    }

    inner class ViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        fun bindItems(data: Category, listener: OnItemClickListener?) {
            val categoryText: TextView = itemView.findViewById(R.id.category_title)
            val categoryImage: ImageView = itemView.findViewById(R.id.category_image)
            val categoryImageCard: androidx.cardview.widget.CardView = itemView.findViewById(R.id.category_image_card)

            categoryText.text = data.text
            categoryImageCard.setBackgroundColor(getMenuIndexColor(context!!, adapterPosition))


            /*
            val requestOptions = RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.RESOURCE).
            Glide.with(categoryImage.context)
                    .load(data.imageDrawableResId)
                    .apply(requestOptions)
                    .transition(withCrossFade())
                    .into(categoryImage)
            */

            Picasso.get()
                    .load(data.imageDrawableResId)
                    .fit()
                    .centerInside()
                    .into(categoryImage)


            if (listener != null) {
                categoryImageCard.setOnClickListener({ _ -> listener.onItemClick(data) })
            }

        }
    }
}