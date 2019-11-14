package com.example.twigaroll.home.gallery_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.twigaroll.R
import com.example.twigaroll.databinding.FragmentImageDetailBinding

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