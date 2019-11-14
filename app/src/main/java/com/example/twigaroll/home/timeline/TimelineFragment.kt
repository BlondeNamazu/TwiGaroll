package com.example.twigaroll.home.timeline

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.twigaroll.R
import com.example.twigaroll.databinding.FragmentHomeBinding
import com.example.twigaroll.home.HomeActivity
import com.twitter.sdk.android.core.models.Tweet
import kotlinx.android.synthetic.main.fragment_home.*

class TimelineFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: TweetAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = TweetAdapter(requireContext())
        timeline_listview.adapter = adapter
        binding.viewModel?.tweetList?.observe(this, Observer<List<Tweet>> {
            adapter.replaceList(it)
            adapter.notifyDataSetChanged()
        })

        timeline_refresh_layout.setOnRefreshListener {
            binding.viewModel?.refreshTimeline(timeline_listview)
            timeline_refresh_layout.isRefreshing = false
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

}