package com.example.twigaroll.home.timeline

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.twigaroll.R
import com.example.twigaroll.data.TweetIdData
import com.example.twigaroll.databinding.ReplyStampListItemBinding
import com.example.twigaroll.util.BindingViewHolder
import com.example.twigaroll.util.FileIORepository
import com.example.twigaroll.util.TweetRequestRepository
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class StampListAdapter @Inject constructor(
    private val fileIORepository: FileIORepository,
    private val tweetRequestRepository: TweetRequestRepository
) :
    RecyclerView.Adapter<BindingViewHolder>() {

    private val _imageURLs = MutableLiveData<List<String>>()
    val imageURLs: LiveData<List<String>>
        get() = _imageURLs
    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val converter: JsonAdapter<TweetIdData> = moshi.adapter(TweetIdData::class.java)
    var inReplyToStatusId: Long = -1

    fun loadStamps(v: View) {
        val json = fileIORepository.readFile(v.context)
        val data = if (json.isEmpty()) {
            TweetIdData(emptyArray())
        } else {
            converter.fromJson(json) ?: TweetIdData(emptyArray())
        }
        _imageURLs.value = data.mediaURLs.toList()
    }

    override fun getItemCount(): Int = imageURLs.value?.size ?: 0

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        (holder.binding as ReplyStampListItemBinding).imageURL =
            imageURLs.value?.get(position)
        holder.binding.stampImage.setOnClickListener {
            if (inReplyToStatusId < 0) return@setOnClickListener
            GlobalScope.launch {
                runBlocking(Dispatchers.IO) {
                    tweetRequestRepository.postStamp(holder.binding.root.context, inReplyToStatusId, "")
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BindingViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.reply_stamp_list_item, parent, false)
        )
}
