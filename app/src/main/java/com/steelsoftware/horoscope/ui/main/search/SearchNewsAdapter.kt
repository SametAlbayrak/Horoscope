package com.steelsoftware.horoscope.ui.main.search

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.steelsoftware.horoscope.R
import com.steelsoftware.horoscope.databinding.RvItemSearchBinding
import com.steelsoftware.horoscope.model.Articles
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * Created by ansh on 16/03/18.
 */
class SearchNewsAdapter(private var items: List<Articles>,
                        private var listener: SearchNewsAdapter.OnItemClickListener)
    : androidx.recyclerview.widget.RecyclerView.Adapter<SearchNewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val binding = RvItemSearchBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(items[position], listener)
    }

    override fun getItemCount(): Int = items.size

    interface OnItemClickListener {
        fun onItemClick(article: Articles)
    }

    fun replaceData(arrayList: List<Articles>) {
        items = arrayList
        notifyDataSetChanged()
    }


    // ViewHolder takes instance of RvItemNewsBinding type instead of View type so
    // we can implement Data Binding in ViewHolder for each item
    inner class ViewHolder(private var binding: RvItemSearchBinding) :
            androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {

        fun bind(news: Articles, listener: OnItemClickListener?) {
            binding.news = news

            val requestOptions = RequestOptions().error(R.drawable.noimg)
            Glide.with(binding.newsThumbnail)
                    .load(news.urlToImage)
                    .apply(requestOptions)
                    .into(binding.newsThumbnail)

            if (listener != null) {
                binding.root.setOnClickListener({ _ -> listener.onItemClick(news) })
            }

            binding.executePendingBindings()
        }
    }
}