package com.example.twigaroll.home.timeline

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.twigaroll.R
import com.example.twigaroll.data.TimelineListItem
import com.example.twigaroll.data.TweetIdData
import com.example.twigaroll.databinding.*
import com.example.twigaroll.util.BindingViewHolder
import com.example.twigaroll.util.FileIORepository
import com.example.twigaroll.util.TweetRequestRepository
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi

import com.twitter.sdk.android.core.models.Tweet
import kotlinx.coroutines.*
import javax.inject.Inject

class TweetRecyclerAdapter @Inject constructor(
    private val fileIORepository: FileIORepository,
    private val tweetRequestRepository: TweetRequestRepository
) :
    RecyclerView.Adapter<BindingViewHolder>() {
    var tweetList = emptyList<TimelineListItem>().toMutableList()
    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val converter: JsonAdapter<TweetIdData> = moshi.adapter(TweetIdData::class.java)

    companion object {
        private const val VIEW_TYPE_ONE_IMAGE = 1
        private const val VIEW_TYPE_TWO_IMAGE = 2
        private const val VIEW_TYPE_THREE_IMAGE = 3
        private const val VIEW_TYPE_FOUR_IMAGE = 4
        private const val VIEW_TYPE_NO_IMAGE = 5
    }

    override fun getItemCount() = tweetList.size

    override fun getItemViewType(position: Int): Int {
        return when (tweetList[position]) {
            is TimelineListItem.NoImageTweet -> VIEW_TYPE_NO_IMAGE
            is TimelineListItem.OneImageTweet -> VIEW_TYPE_ONE_IMAGE
            is TimelineListItem.TwoImageTweet -> VIEW_TYPE_TWO_IMAGE
            is TimelineListItem.ThreeImageTweet -> VIEW_TYPE_THREE_IMAGE
            is TimelineListItem.FourImageTweet -> VIEW_TYPE_FOUR_IMAGE
        }
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        when (val tweetListItem = tweetList[position]) {
            is TimelineListItem.NoImageTweet -> {
                val thisTweet = tweetListItem.tweet
                (holder.binding as TweetRowImage0Binding).tweet = thisTweet
                holder.binding.favButton.setOnClickListener {

                    GlobalScope.launch {
                        runBlocking(Dispatchers.IO) {
                            tweetList[position] =
                                if (thisTweet.favorited) {
                                    TimelineListItem.NoImageTweet(
                                        tweet = tweetRequestRepository.postUnlike(thisTweet.id)
                                    )
                                } else {
                                    TimelineListItem.NoImageTweet(
                                        tweet = tweetRequestRepository.postLike(thisTweet.id)
                                    )
                                }
                        }
                        withContext(Dispatchers.Main) {
                            notifyItemChanged(position)
                        }
                    }
                }
                holder.binding.retweetButton.setOnClickListener {

                    GlobalScope.launch {
                        runBlocking(Dispatchers.IO) {
                            tweetList[position] =
                                if (thisTweet.retweeted) {
                                    TimelineListItem.NoImageTweet(
                                        tweet = tweetRequestRepository.postUnretweet(thisTweet.id)
                                    )
                                } else {
                                    TimelineListItem.NoImageTweet(
                                        tweet = tweetRequestRepository.postRetweet(thisTweet.id)
                                    )
                                }
                        }
                        withContext(Dispatchers.Main) {
                            notifyItemChanged(position)
                        }
                    }
                }
            }
            is TimelineListItem.OneImageTweet -> {
                val thisTweet = tweetListItem.tweet
                (holder.binding as TweetRowImage1Binding).tweet = thisTweet
                holder.binding.belongToGallery =
                    belongToGallery(thisTweet, holder.binding.root.context)
                holder.binding.baseTweet.favButton.setOnClickListener {

                    GlobalScope.launch {
                        runBlocking(Dispatchers.IO) {
                            tweetList[position] =
                                if (thisTweet.favorited) {
                                    TimelineListItem.OneImageTweet(
                                        tweet = tweetRequestRepository.postUnlike(thisTweet.id),
                                        belongToGallery = tweetListItem.belongToGallery
                                    )
                                } else {
                                    TimelineListItem.OneImageTweet(
                                        tweet = tweetRequestRepository.postLike(thisTweet.id),
                                        belongToGallery = tweetListItem.belongToGallery
                                    )
                                }
                        }
                        withContext(Dispatchers.Main) {
                            notifyItemChanged(position)
                        }
                    }
                }
                holder.binding.baseTweet.retweetButton.setOnClickListener {

                    GlobalScope.launch {
                        runBlocking(Dispatchers.IO) {
                            tweetList[position] =
                                if (thisTweet.retweeted) {
                                    TimelineListItem.OneImageTweet(
                                        tweet = tweetRequestRepository.postUnretweet(thisTweet.id),
                                        belongToGallery = tweetListItem.belongToGallery
                                    )
                                } else {
                                    TimelineListItem.OneImageTweet(
                                        tweet = tweetRequestRepository.postRetweet(thisTweet.id),
                                        belongToGallery = tweetListItem.belongToGallery
                                    )
                                }
                        }
                        withContext(Dispatchers.Main) {
                            notifyItemChanged(position)
                        }
                    }
                }
                holder.binding.stockButton.setOnClickListener {
                    GlobalScope.launch {
                        runBlocking(Dispatchers.IO) {
                            tweetList[position] =
                                if (tweetListItem.belongToGallery) {
                                    TimelineListItem.OneImageTweet(
                                        tweet = thisTweet,
                                        belongToGallery = unStock(
                                            thisTweet,
                                            holder.binding.root.context
                                        )
                                    )
                                } else {
                                    TimelineListItem.OneImageTweet(
                                        tweet = tweetRequestRepository.postRetweet(thisTweet.id),
                                        belongToGallery = stock(
                                            thisTweet,
                                            holder.binding.root.context
                                        )
                                    )
                                }
                        }
                        withContext(Dispatchers.Main) {
                            notifyItemChanged(position)
                        }
                    }
                }
            }
            is TimelineListItem.TwoImageTweet -> {
                val thisTweet = tweetListItem.tweet
                (holder.binding as TweetRowImage2Binding).tweet = thisTweet
                holder.binding.belongToGallery =
                    belongToGallery(thisTweet, holder.binding.root.context)
                holder.binding.baseTweet.favButton.setOnClickListener {

                    GlobalScope.launch {
                        runBlocking(Dispatchers.IO) {
                            tweetList[position] =
                                if (thisTweet.favorited) {
                                    TimelineListItem.TwoImageTweet(
                                        tweet = tweetRequestRepository.postUnlike(thisTweet.id),
                                        belongToGallery = tweetListItem.belongToGallery
                                    )
                                } else {
                                    TimelineListItem.TwoImageTweet(
                                        tweet = tweetRequestRepository.postLike(thisTweet.id),
                                        belongToGallery = tweetListItem.belongToGallery
                                    )
                                }
                        }
                        withContext(Dispatchers.Main) {
                            notifyItemChanged(position)
                        }
                    }
                }
                holder.binding.baseTweet.retweetButton.setOnClickListener {

                    GlobalScope.launch {
                        runBlocking(Dispatchers.IO) {
                            tweetList[position] =
                                if (thisTweet.retweeted) {
                                    TimelineListItem.TwoImageTweet(
                                        tweet = tweetRequestRepository.postUnretweet(thisTweet.id),
                                        belongToGallery = tweetListItem.belongToGallery
                                    )
                                } else {
                                    TimelineListItem.TwoImageTweet(
                                        tweet = tweetRequestRepository.postRetweet(thisTweet.id),
                                        belongToGallery = tweetListItem.belongToGallery
                                    )
                                }
                        }
                        withContext(Dispatchers.Main) {
                            notifyItemChanged(position)
                        }
                    }
                }
                holder.binding.stockButton.setOnClickListener {
                    GlobalScope.launch {
                        runBlocking(Dispatchers.IO) {
                            tweetList[position] =
                                if (tweetListItem.belongToGallery) {
                                    TimelineListItem.TwoImageTweet(
                                        tweet = thisTweet,
                                        belongToGallery = unStock(
                                            thisTweet,
                                            holder.binding.root.context
                                        )
                                    )
                                } else {
                                    TimelineListItem.TwoImageTweet(
                                        tweet = tweetRequestRepository.postRetweet(thisTweet.id),
                                        belongToGallery = stock(
                                            thisTweet,
                                            holder.binding.root.context
                                        )
                                    )
                                }
                        }
                        withContext(Dispatchers.Main) {
                            notifyItemChanged(position)
                        }
                    }
                }
            }
            is TimelineListItem.ThreeImageTweet -> {
                val thisTweet = tweetListItem.tweet
                (holder.binding as TweetRowImage3Binding).tweet = thisTweet
                holder.binding.belongToGallery =
                    belongToGallery(thisTweet, holder.binding.root.context)
                holder.binding.baseTweet.favButton.setOnClickListener {

                    GlobalScope.launch {
                        runBlocking(Dispatchers.IO) {
                            tweetList[position] =
                                if (thisTweet.favorited) {
                                    TimelineListItem.ThreeImageTweet(
                                        tweet = tweetRequestRepository.postUnlike(thisTweet.id),
                                        belongToGallery = tweetListItem.belongToGallery
                                    )
                                } else {
                                    TimelineListItem.ThreeImageTweet(
                                        tweet = tweetRequestRepository.postLike(thisTweet.id),
                                        belongToGallery = tweetListItem.belongToGallery
                                    )
                                }
                        }
                        withContext(Dispatchers.Main) {
                            notifyItemChanged(position)
                        }
                    }
                }
                holder.binding.baseTweet.retweetButton.setOnClickListener {

                    GlobalScope.launch {
                        runBlocking(Dispatchers.IO) {
                            tweetList[position] =
                                if (thisTweet.retweeted) {
                                    TimelineListItem.ThreeImageTweet(
                                        tweet = tweetRequestRepository.postUnretweet(thisTweet.id),
                                        belongToGallery = tweetListItem.belongToGallery
                                    )
                                } else {
                                    TimelineListItem.ThreeImageTweet(
                                        tweet = tweetRequestRepository.postRetweet(thisTweet.id),
                                        belongToGallery = tweetListItem.belongToGallery
                                    )
                                }
                        }
                        withContext(Dispatchers.Main) {
                            notifyItemChanged(position)
                        }
                    }
                }
                holder.binding.stockButton.setOnClickListener {
                    GlobalScope.launch {
                        runBlocking(Dispatchers.IO) {
                            tweetList[position] =
                                if (tweetListItem.belongToGallery) {
                                    TimelineListItem.ThreeImageTweet(
                                        tweet = thisTweet,
                                        belongToGallery = unStock(
                                            thisTweet,
                                            holder.binding.root.context
                                        )
                                    )
                                } else {
                                    TimelineListItem.ThreeImageTweet(
                                        tweet = tweetRequestRepository.postRetweet(thisTweet.id),
                                        belongToGallery = stock(
                                            thisTweet,
                                            holder.binding.root.context
                                        )
                                    )
                                }
                        }
                        withContext(Dispatchers.Main) {
                            notifyItemChanged(position)
                        }
                    }
                }
            }
            is TimelineListItem.FourImageTweet -> {
                val thisTweet = tweetListItem.tweet
                (holder.binding as TweetRowImage4Binding).tweet = thisTweet
                holder.binding.belongToGallery =
                    belongToGallery(thisTweet, holder.binding.root.context)
                holder.binding.baseTweet.favButton.setOnClickListener {

                    GlobalScope.launch {
                        runBlocking(Dispatchers.IO) {
                            tweetList[position] =
                                if (thisTweet.favorited) {
                                    TimelineListItem.FourImageTweet(
                                        tweet = tweetRequestRepository.postUnlike(thisTweet.id),
                                        belongToGallery = tweetListItem.belongToGallery
                                    )
                                } else {
                                    TimelineListItem.FourImageTweet(
                                        tweet = tweetRequestRepository.postLike(thisTweet.id),
                                        belongToGallery = tweetListItem.belongToGallery
                                    )
                                }
                        }
                        withContext(Dispatchers.Main) {
                            notifyItemChanged(position)
                        }
                    }
                }
                holder.binding.baseTweet.retweetButton.setOnClickListener {

                    GlobalScope.launch {
                        runBlocking(Dispatchers.IO) {
                            tweetList[position] =
                                if (thisTweet.retweeted) {
                                    TimelineListItem.FourImageTweet(
                                        tweet = tweetRequestRepository.postUnretweet(thisTweet.id),
                                        belongToGallery = tweetListItem.belongToGallery
                                    )
                                } else {
                                    TimelineListItem.FourImageTweet(
                                        tweet = tweetRequestRepository.postRetweet(thisTweet.id),
                                        belongToGallery = tweetListItem.belongToGallery
                                    )
                                }
                        }
                        withContext(Dispatchers.Main) {
                            notifyItemChanged(position)
                        }
                    }
                }
                holder.binding.stockButton.setOnClickListener {
                    GlobalScope.launch {
                        runBlocking(Dispatchers.IO) {
                            tweetList[position] =
                                if (tweetListItem.belongToGallery) {
                                    TimelineListItem.FourImageTweet(
                                        tweet = thisTweet,
                                        belongToGallery = unStock(
                                            thisTweet,
                                            holder.binding.root.context
                                        )
                                    )
                                } else {
                                    TimelineListItem.FourImageTweet(
                                        tweet = tweetRequestRepository.postRetweet(thisTweet.id),
                                        belongToGallery = stock(
                                            thisTweet,
                                            holder.binding.root.context
                                        )
                                    )
                                }
                        }
                        withContext(Dispatchers.Main) {
                            notifyItemChanged(position)
                        }
                    }
                }
            }
        }
    }

    override fun getItemId(position: Int) = tweetList[position].tweet.id

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BindingViewHolder(
            LayoutInflater.from(parent.context).inflate(
                when (viewType) {
                    VIEW_TYPE_NO_IMAGE -> R.layout.tweet_row_image_0
                    VIEW_TYPE_ONE_IMAGE -> R.layout.tweet_row_image_1
                    VIEW_TYPE_TWO_IMAGE -> R.layout.tweet_row_image_2
                    VIEW_TYPE_THREE_IMAGE -> R.layout.tweet_row_image_3
                    VIEW_TYPE_FOUR_IMAGE -> R.layout.tweet_row_image_4
                    else -> R.layout.tweet_row_image_0
                }, parent, false
            )
        )

    fun replaceList(newList: List<Tweet>, context: Context?) {
        context ?: return
        tweetList.clear()
        tweetList.addAll(
            newList.map {
                val tweetToShow = it.retweetedStatus ?: it
                if (tweetToShow.entities.media.isEmpty()) {
                    TimelineListItem.NoImageTweet(
                        tweetToShow
                    )
                } else {
                    when (tweetToShow.extendedEntities.media.size) {
                        1 -> TimelineListItem.OneImageTweet(
                            tweetToShow,
                            belongToGallery(tweetToShow, context)
                        )
                        2 -> TimelineListItem.TwoImageTweet(
                            tweetToShow,
                            belongToGallery(tweetToShow, context)
                        )
                        3 -> TimelineListItem.ThreeImageTweet(
                            tweetToShow,
                            belongToGallery(tweetToShow, context)
                        )
                        4 -> TimelineListItem.FourImageTweet(
                            tweetToShow,
                            belongToGallery(tweetToShow, context)
                        )
                        else -> TimelineListItem.NoImageTweet(
                            tweetToShow
                        )
                    }
                }
            }
        )
        notifyDataSetChanged()
    }

    private fun stock(tweet: Tweet, context: Context): Boolean {
        val json = fileIORepository.readFile(context)
        val data = if (json.isEmpty()) {
            TweetIdData(emptyArray())
        } else {
            converter.fromJson(json) ?: TweetIdData(emptyArray())
        }
        val savedURLs = data.mediaURLs
        val newURLs = tweet.extendedEntities.media.map { it.mediaUrlHttps }.toTypedArray()

        val newData = TweetIdData(newURLs + savedURLs)

        fileIORepository.writeFile(
            context = context,
            string = converter.toJson(newData)
        )

        return true
    }

    private fun unStock(tweet: Tweet, context: Context): Boolean {
        val json = fileIORepository.readFile(context)
        val data = if (json.isEmpty()) {
            TweetIdData(emptyArray())
        } else {
            converter.fromJson(json) ?: TweetIdData(emptyArray())
        }
        val savedURLs = data.mediaURLs
        val newURLs = tweet.extendedEntities.media.map { it.mediaUrlHttps }.toTypedArray()

        val newData = TweetIdData(savedURLs.filterNot { newURLs.contains(it) }.toTypedArray())

        fileIORepository.writeFile(
            context = context,
            string = converter.toJson(newData)
        )

        return false
    }

    private fun belongToGallery(tweet: Tweet, context: Context): Boolean {
        val json = fileIORepository.readFile(context)
        val data = if (json.isEmpty()) {
            TweetIdData(emptyArray())
        } else {
            converter.fromJson(json) ?: TweetIdData(emptyArray())
        }
        val savedURLs = data.mediaURLs
        val newURLs = tweet.extendedEntities.media.map { it.mediaUrlHttps }.toTypedArray()

        return newURLs
            .map { savedURLs.contains(it) }
            .reduce { a, b -> a or b }
    }
}