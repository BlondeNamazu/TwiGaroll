package com.example.twigaroll

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.twigaroll.databinding.FragmentGalleryBinding
import kotlinx.android.synthetic.main.fragment_gallery.*

class GalleryFragment : Fragment() {
    private lateinit var binding: FragmentGalleryBinding
    private lateinit var adapter: TweetEmbeddedImageAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = TweetEmbeddedImageAdapter(requireContext())
        gallery_listview.adapter = adapter
        binding.viewModel?.imageURLs?.observe(this, Observer<List<String>> {
            adapter.replaceList(it)
            adapter.notifyDataSetChanged()
        })

        gallery_refresh_layout.setOnRefreshListener {
            binding.viewModel?.refreshTimeline(gallery_listview)
            gallery_refresh_layout.isRefreshing = false
        }

        binding.viewModel?.refreshTimeline(gallery_listview)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gallery, container, false)
        binding = FragmentGalleryBinding.bind(view).apply {
            viewModel = (activity as HomeActivity).obtainGalleryViewModel()
            lifecycleOwner = this.lifecycleOwner
            galleryListview.onItemClickListener =
                AdapterView.OnItemClickListener { _, _, position, _ ->
                    val bundle = Bundle()
                    bundle.putString(
                        "imageURL",
                        binding.viewModel?.imageURLs?.value?.get(position)
                            ?: return@OnItemClickListener
                    )
                    val detailFragment = GalleryDetailFragment()
                    detailFragment.arguments = bundle
                    val fm = fragmentManager ?: return@OnItemClickListener
                    fm.beginTransaction()
                        .add(R.id.gallery_container, detailFragment)
                        .commit()
                }
        }
        return view
    }
}