package com.example.twigaroll

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import com.example.twigaroll.databinding.TweetEntityImageBinding

class TweetEmbeddedImageAdapter(private val context: Context) :
    BaseAdapter() {
    private var imageURLs = emptyList<String>()

    override fun getCount(): Int {
        return imageURLs.size
    }

    override fun getItem(position: Int): Any {
        return imageURLs[position]
    }

    override fun getItemId(position: Int): Long {
        return imageURLs[position].hashCode().toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = if (convertView == null) {
            val binding: TweetEntityImageBinding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.tweet_entity_image,
                parent,
                false

            )
            binding.root.tag = binding
            binding
        } else {
            convertView.tag as TweetEntityImageBinding
        }.apply {
            imageurl = imageURLs[position]
        }
        return binding.root
    }

    fun replaceList(newList: List<String>) {
        imageURLs = newList
    }
}
