package com.example.twigaroll

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.twigaroll.databinding.FragmentHomeBinding
import com.twitter.sdk.android.core.models.Tweet
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private lateinit var adapter: TweetAdapter
    private var tweetList = emptyList<Tweet>().toMutableList()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = TweetAdapter(requireContext(), tweetList)
        timeline_listview.adapter = adapter
        binding.viewModel?.tweetList?.observe(this, Observer<List<Tweet>> {
            tweetList.removeAll(tweetList)
            tweetList.addAll(it)
            adapter.notifyDataSetChanged()
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        binding = FragmentHomeBinding.bind(view).apply {
            viewModel = (activity as HomeActivity).obtainViewModel()
        }
        binding.lifecycleOwner = this.viewLifecycleOwner
        return view
    }

}