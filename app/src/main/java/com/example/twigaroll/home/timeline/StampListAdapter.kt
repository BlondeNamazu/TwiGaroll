package com.example.twigaroll.home.timeline

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.twigaroll.R
import com.example.twigaroll.databinding.ReplyStampListItemBinding
import com.example.twigaroll.util.BindingViewHolder
import com.example.twigaroll.util.TweetRequestRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.reflect.Modifier
import javax.inject.Inject

class StampListAdapter @Inject constructor(
    private val tweetRequestRepository: TweetRequestRepository
) :
    RecyclerView.Adapter<BindingViewHolder>() {

    private lateinit var stampResourceIds: List<Int>
    var inReplyToStatusId: Long = -1

    fun loadStamps(v: View) {
        val fields = R.drawable::class.java.fields
            .filter {
                Modifier.isStatic(it.modifiers)
                        && Modifier.isPublic(it.modifiers)
                        && Modifier.isFinal(it.modifiers)
            }
            .filter {
                it.type == java.lang.Integer.TYPE
            }
            .filter {
                println(it.name)
                it.name.contains("stamp")
            }
            .map {
                it.getInt(null)
            }
        stampResourceIds = fields
    }

    override fun getItemCount(): Int = stampResourceIds.size

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        val resourceId = stampResourceIds[position]
        (holder.binding as ReplyStampListItemBinding).resourceId = resourceId
        holder.binding.stampImage.setOnClickListener {
            if (inReplyToStatusId < 0) return@setOnClickListener
            GlobalScope.launch {
                runBlocking(Dispatchers.IO) {
                    tweetRequestRepository.postStamp(
                        holder.binding.root.context,
                        inReplyToStatusId,
                        resourceId
                    )
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
