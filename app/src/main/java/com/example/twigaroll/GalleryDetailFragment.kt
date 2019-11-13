package com.example.twigaroll

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.twigaroll.databinding.FragmentGalleryBinding
import com.example.twigaroll.databinding.FragmentImageDetailBinding
import kotlinx.android.synthetic.main.fragment_gallery.*
import kotlinx.android.synthetic.main.fragment_image_detail.*
import kotlinx.android.synthetic.main.tweet_entity_image.*

class GalleryDetailFragment : Fragment() {
    private lateinit var binding: FragmentImageDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_image_detail, container, false)
        val url = arguments?.getString("imageURL") ?: return view
        binding = FragmentImageDetailBinding.bind(view).apply {
            imageURL = url
            lifecycleOwner = this.lifecycleOwner
            imageDetailContainer.setOnClickListener {
                fragmentManager?.beginTransaction()?.remove(this@GalleryDetailFragment)?.commit()
            }
        }
        return view
    }

}