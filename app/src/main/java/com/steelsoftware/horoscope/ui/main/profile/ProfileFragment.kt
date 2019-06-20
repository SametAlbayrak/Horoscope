package com.steelsoftware.horoscope.ui.main.profile

import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.steelsoftware.horoscope.R
import com.steelsoftware.horoscope.model.Articles
import com.steelsoftware.horoscope.ui.main.search.SearchViewModel
import dagger.android.support.DaggerFragment

class ProfileFragment : Fragment() {

/*
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(SearchViewModel::class.java)



        binding.viewModel = viewModel
        binding.executePendingBindings()

        binding.repositoryRv.layoutManager = LinearLayoutManager(activity)
        binding.repositoryRv.adapter = repositoryRecyclerViewAdapter

        binding.clearSearch.setOnClickListener(View.OnClickListener {
            binding.searchEditText.text?.clear()
            // refresh adapter to remove previous results


        })



        viewModel.news.observe(this,
                Observer<List<Articles>> { it?.let { repositoryRecyclerViewAdapter.replaceData(it) } })

        viewModel.getStatus().observe(this, Observer { handleStatus(it) })

    }*/
}