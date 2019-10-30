package com.example.twigaroll

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.twigaroll.databinding.TweetRowBinding

import com.twitter.sdk.android.core.models.Tweet

class TweetAdapter(private val context: Context, private val tweetList: List<Tweet>) :
    BaseAdapter() {
    private val layoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return tweetList.size
    }

    override fun getItem(position: Int): Any {
        return tweetList[position]
    }

    override fun getItemId(position: Int): Long {
        return tweetList[position].getId()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = if (convertView == null) {
            val binding: TweetRowBinding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.tweet_row,
                parent,
                false
            )
            binding.root.tag = binding
            binding
        } else {
            convertView.tag as TweetRowBinding
        }
        binding.apply {
            tweet = tweetList[position]
        }
        return binding.root
    }
}