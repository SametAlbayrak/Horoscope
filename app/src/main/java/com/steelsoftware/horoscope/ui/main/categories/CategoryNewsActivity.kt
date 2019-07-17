package com.steelsoftware.horoscope.ui.main.categories

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import android.content.Context
import androidx.databinding.DataBindingUtil
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import androidx.browser.customtabs.CustomTabsIntent
import com.google.android.material.snackbar.Snackbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.steelsoftware.horoscope.R
import com.steelsoftware.horoscope.databinding.ActivityCategoryNewsBinding
import com.steelsoftware.horoscope.model.Articles
import com.steelsoftware.horoscope.ui.main.detail.adapter.TaskAdapter
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_category_news.*
import javax.inject.Inject

class CategoryNewsActivity : DaggerAppCompatActivity(), CategoryNewsAdapter.OnItemClickListener {

    // FragmentHomeBinding class is generated at compile time so build the project first
    // lateinit modifier allows us to have non-null variables waiting for initialization
    private lateinit var binding: ActivityCategoryNewsBinding

    private val taskAdapter = TaskAdapter()


    // arrayListOf() returns an empty new arrayList
    //   private val repositoryRecyclerViewAdapter = CategoryNewsAdapter(arrayListOf(), this)

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: CategoryViewModel

    private lateinit var category: String

    private var builder: CustomTabsIntent.Builder = CustomTabsIntent.Builder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_category_news)

        val intent = intent
        if (intent.hasExtra("CATEGORY")) {
            category = intent.getStringExtra("CATEGORY")
            title = category
        }

        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(CategoryViewModel::class.java)

        /*   if (viewModel.getNewsCategory() == null) {
               viewModel.setNewsCategory(category)
           }
   */

        binding.viewModel = viewModel
        binding.executePendingBindings()


        rv_yorumlar.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_yorumlar.adapter = taskAdapter


        viewModel.taskList.observe(this, Observer {
            taskAdapter.setItems(it)
        })
    }


    override fun onItemClick(article: Articles) {
        val isConnected = isConnectedToInternet()
        if (isConnected) {
            builder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary))
            builder.setSecondaryToolbarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
            builder.setStartAnimations(this, R.anim.slide_in_right, R.anim.slide_out_left)
            builder.setExitAnimations(this, android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right)
            builder.build().launchUrl(this, Uri.parse(article.url))
        } else {
            Snackbar.make(binding.constraintLayout, "You are not connected to the internet", Snackbar.LENGTH_LONG).show()
        }

    }

    private fun isConnectedToInternet(): Boolean {
        val connManager = this.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager

        val ni = connManager.activeNetworkInfo
        return ni != null && ni.isConnected
    }
}
