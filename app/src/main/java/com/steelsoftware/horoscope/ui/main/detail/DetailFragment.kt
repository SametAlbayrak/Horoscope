package com.steelsoftware.horoscope.ui.main.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.steelsoftware.horoscope.R
import com.steelsoftware.horoscope.databinding.FragmentDetailBinding
import com.steelsoftware.horoscope.ui.main.detail.adapter.TaskAdapter
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_detail.*
import javax.inject.Inject

class DetailFragment : DaggerFragment() {


    private val taskAdapter = TaskAdapter()

    private lateinit var binding: FragmentDetailBinding

    companion object {
        fun newInstance() = DetailFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(DetailViewModel::class.java)


        binding.viewModel = viewModel
        binding.executePendingBindings()

        recyclerview.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        recyclerview.adapter = taskAdapter


        viewModel.taskList.observe(this, Observer {
            taskAdapter.setItems(it)
        })

        /*  addTask.setOnClickListener {
              viewModel.addTask(taskTitle.text.toString())
              taskTitle.text?.clear()
          }
  */

    }
}