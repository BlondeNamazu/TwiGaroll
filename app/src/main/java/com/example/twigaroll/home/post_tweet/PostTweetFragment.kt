package com.example.twigaroll.home.post_tweet

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.example.twigaroll.R
import com.example.twigaroll.databinding.FragmentPostTweetBinding
import com.example.twigaroll.home.HomeActivity

class PostTweetFragment : Fragment() {
    private lateinit var binding: FragmentPostTweetBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_post_tweet, container, false)
        binding = FragmentPostTweetBinding.bind(view).apply {
            viewModel = (activity as HomeActivity).obtainTimelineViewModel()
            lifecycleOwner = this.lifecycleOwner
            tweetButton.setOnClickListener {
                viewModel?.postTwewt(tweetText.text.toString())
                tweetText.text.clear()
                fragmentManager?.beginTransaction()?.remove(this@PostTweetFragment)?.commit()
                val manager = (activity as HomeActivity)
                    .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(
                    (activity as HomeActivity).currentFocus.windowToken,
                    0
                )
            }
        }
        return view
    }

}