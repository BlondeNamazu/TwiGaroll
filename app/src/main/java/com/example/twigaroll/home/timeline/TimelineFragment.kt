package com.example.twigaroll.home.timeline

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.twigaroll.R
import com.example.twigaroll.RepositoryModule
import com.example.twigaroll.databinding.FragmentHomeBinding
import com.example.twigaroll.home.HomeActivity
import com.twitter.sdk.android.core.models.Tweet
import dagger.Component
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Singleton

class TimelineFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: TweetRecyclerAdapter

    @SuppressLint("RestrictedApi")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = DaggerTimelineFragment_TweetAdapterFactory.create().make()
        timeline_listview.layoutManager = LinearLayoutManager(context)
        timeline_listview.adapter = adapter
        binding.viewModel?.tweetList?.observe(this, Observer<List<Tweet>> {
            adapter.replaceList(it, context)
        })
        binding.viewModel?.shouldBackToTimelineTop?.observe(this, Observer<Boolean> {
            if (binding.viewModel?.shouldBackToTimelineTop?.value == true) {
                timeline_listview.smoothScrollToPosition(0)
                binding.viewModel?.toggleShouldBackToTimelineTop(false)
            }
        })

        timeline_refresh_layout.setOnRefreshListener {
            binding.viewModel?.refreshTimeline(timeline_listview)
            timeline_refresh_layout.isRefreshing = false
        }

        fab.setOnClickListener {
            binding.viewModel?.beginPostTweetFragment()
            fab.visibility = View.GONE
        }

        binding.viewModel?.refreshTimeline(timeline_listview)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        binding = FragmentHomeBinding.bind(view).apply {
            viewModel = (activity as HomeActivity).obtainTimelineViewModel()
            lifecycleOwner = this.lifecycleOwner
        }
        return view
    }

    @Singleton
    @Component(modules = [RepositoryModule::class])
    interface TweetAdapterFactory {
        fun make(): TweetRecyclerAdapter
    }
}