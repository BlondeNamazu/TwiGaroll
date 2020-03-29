package com.example.twigaroll.home.timeline

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twigaroll.R
import com.example.twigaroll.RepositoryModule
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
import dagger.Component
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton

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

    private fun postLikeUnlike(item: TimelineListItem, position: Int) {
        GlobalScope.launch {
            runBlocking(Dispatchers.IO) {
                tweetList[position] =
                    if (item.tweet.favorited) {
                        when (item) {
                            is TimelineListItem.NoImageTweet -> TimelineListItem.NoImageTweet(
                                tweet = tweetRequestRepository.postUnlike(item.tweet.id)
                            )
                            is TimelineListItem.OneImageTweet -> TimelineListItem.OneImageTweet(
                                tweet = tweetRequestRepository.postUnlike(item.tweet.id),
                                belongToGallery = item.belongToGallery
                            )
                            is TimelineListItem.TwoImageTweet -> TimelineListItem.TwoImageTweet(
                                tweet = tweetRequestRepository.postUnlike(item.tweet.id),
                                belongToGallery = item.belongToGallery
                            )
                            is TimelineListItem.ThreeImageTweet -> TimelineListItem.ThreeImageTweet(
                                tweet = tweetRequestRepository.postUnlike(item.tweet.id),
                                belongToGallery = item.belongToGallery
                            )
                            is TimelineListItem.FourImageTweet -> TimelineListItem.FourImageTweet(
                                tweet = tweetRequestRepository.postUnlike(item.tweet.id),
                                belongToGallery = item.belongToGallery
                            )
                        }
                    } else {
                        when (item) {
                            is TimelineListItem.NoImageTweet -> TimelineListItem.NoImageTweet(
                                tweet = tweetRequestRepository.postLike(item.tweet.id)
                            )
                            is TimelineListItem.OneImageTweet -> TimelineListItem.OneImageTweet(
                                tweet = tweetRequestRepository.postLike(item.tweet.id),
                                belongToGallery = item.belongToGallery
                            )
                            is TimelineListItem.TwoImageTweet -> TimelineListItem.TwoImageTweet(
                                tweet = tweetRequestRepository.postLike(item.tweet.id),
                                belongToGallery = item.belongToGallery
                            )
                            is TimelineListItem.ThreeImageTweet -> TimelineListItem.ThreeImageTweet(
                                tweet = tweetRequestRepository.postLike(item.tweet.id),
                                belongToGallery = item.belongToGallery
                            )
                            is TimelineListItem.FourImageTweet -> TimelineListItem.FourImageTweet(
                                tweet = tweetRequestRepository.postLike(item.tweet.id),
                                belongToGallery = item.belongToGallery
                            )
                        }
                    }
            }
            withContext(Dispatchers.Main) {
                notifyItemChanged(position)
            }
        }
    }

    private fun postRetweetUnretweet(item: TimelineListItem, position: Int) {
        GlobalScope.launch {
            runBlocking(Dispatchers.IO) {
                tweetList[position] =
                    if (item.tweet.retweeted) {
                        when (item) {
                            is TimelineListItem.NoImageTweet -> TimelineListItem.NoImageTweet(
                                tweet = tweetRequestRepository.postUnretweet(item.tweet.id)
                            )
                            is TimelineListItem.OneImageTweet -> TimelineListItem.OneImageTweet(
                                tweet = tweetRequestRepository.postUnretweet(item.tweet.id),
                                belongToGallery = item.belongToGallery
                            )
                            is TimelineListItem.TwoImageTweet -> TimelineListItem.TwoImageTweet(
                                tweet = tweetRequestRepository.postUnretweet(item.tweet.id),
                                belongToGallery = item.belongToGallery
                            )
                            is TimelineListItem.ThreeImageTweet -> TimelineListItem.ThreeImageTweet(
                                tweet = tweetRequestRepository.postUnretweet(item.tweet.id),
                                belongToGallery = item.belongToGallery
                            )
                            is TimelineListItem.FourImageTweet -> TimelineListItem.FourImageTweet(
                                tweet = tweetRequestRepository.postUnretweet(item.tweet.id),
                                belongToGallery = item.belongToGallery
                            )
                        }
                    } else {
                        when (item) {
                            is TimelineListItem.NoImageTweet -> TimelineListItem.NoImageTweet(
                                tweet = tweetRequestRepository.postRetweet(item.tweet.id)
                            )
                            is TimelineListItem.OneImageTweet -> TimelineListItem.OneImageTweet(
                                tweet = tweetRequestRepository.postRetweet(item.tweet.id),
                                belongToGallery = item.belongToGallery
                            )
                            is TimelineListItem.TwoImageTweet -> TimelineListItem.TwoImageTweet(
                                tweet = tweetRequestRepository.postRetweet(item.tweet.id),
                                belongToGallery = item.belongToGallery
                            )
                            is TimelineListItem.ThreeImageTweet -> TimelineListItem.ThreeImageTweet(
                                tweet = tweetRequestRepository.postRetweet(item.tweet.id),
                                belongToGallery = item.belongToGallery
                            )
                            is TimelineListItem.FourImageTweet -> TimelineListItem.FourImageTweet(
                                tweet = tweetRequestRepository.postRetweet(item.tweet.id),
                                belongToGallery = item.belongToGallery
                            )
                        }
                    }
            }
            withContext(Dispatchers.Main) {
                notifyItemChanged(position)
            }
        }
    }

    private fun postStockUnstock(item: TimelineListItem, position: Int, context: Context) {
        GlobalScope.launch {
            runBlocking(Dispatchers.IO) {
                tweetList[position] =
                    when (item) {
                        is TimelineListItem.OneImageTweet -> {
                            if (item.belongToGallery) {
                                TimelineListItem.OneImageTweet(
                                    tweet = item.tweet,
                                    belongToGallery = unStock(item.tweet, context)
                                )
                            } else {
                                TimelineListItem.OneImageTweet(
                                    tweet = item.tweet,
                                    belongToGallery = stock(item.tweet, context)
                                )
                            }
                        }
                        is TimelineListItem.TwoImageTweet -> {
                            if (item.belongToGallery) {
                                TimelineListItem.TwoImageTweet(
                                    tweet = item.tweet,
                                    belongToGallery = unStock(item.tweet, context)
                                )
                            } else {
                                TimelineListItem.TwoImageTweet(
                                    tweet = item.tweet,
                                    belongToGallery = stock(item.tweet, context)
                                )
                            }
                        }
                        is TimelineListItem.ThreeImageTweet -> {
                            if (item.belongToGallery) {
                                TimelineListItem.ThreeImageTweet(
                                    tweet = item.tweet,
                                    belongToGallery = unStock(item.tweet, context)
                                )
                            } else {
                                TimelineListItem.ThreeImageTweet(
                                    tweet = item.tweet,
                                    belongToGallery = stock(item.tweet, context)
                                )
                            }
                        }
                        is TimelineListItem.FourImageTweet -> {
                            if (item.belongToGallery) {
                                TimelineListItem.FourImageTweet(
                                    tweet = item.tweet,
                                    belongToGallery = unStock(item.tweet, context)
                                )
                            } else {
                                TimelineListItem.FourImageTweet(
                                    tweet = item.tweet,
                                    belongToGallery = stock(item.tweet, context)
                                )
                            }
                        }
                        else -> item
                    }
            }
            withContext(Dispatchers.Main) {
                notifyItemChanged(position)
            }
        }
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        when (val tweetListItem = tweetList[position]) {
            is TimelineListItem.NoImageTweet -> {
                val thisTweet = tweetListItem.tweet
                (holder.binding as TweetRowImage0Binding).tweet = thisTweet
                holder.binding.favButton.setOnClickListener {
                    postLikeUnlike(tweetListItem, position)
                }
                holder.binding.retweetButton.setOnClickListener {
                    postRetweetUnretweet(tweetListItem, position)
                }
                holder.binding.stampListOpened = false
                holder.binding.stampButton.setOnClickListener {
                    val currentStatus = holder.binding.stampListOpened ?: return@setOnClickListener
                    if (!currentStatus) {
                        holder.binding.stampList.layoutManager =
                            LinearLayoutManager(
                                holder.binding.root.context,
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )
                        val adapter =
                            DaggerTweetRecyclerAdapter_StampListAdapterFactory.create().make()
                        holder.binding.stampList.adapter = adapter
                        (holder.binding.stampList.adapter as StampListAdapter).inReplyToStatusId =
                            tweetList[position].tweet.id
                        adapter.loadStamps(holder.binding.stampList)
                    }
                    holder.binding.stampListOpened = !currentStatus
                }
            }
            is TimelineListItem.OneImageTweet -> {
                val thisTweet = tweetListItem.tweet
                (holder.binding as TweetRowImage1Binding).tweet = thisTweet
                holder.binding.belongToGallery =
                    belongToGallery(thisTweet, holder.binding.root.context)
                holder.binding.baseTweet.favButton.setOnClickListener {
                    postLikeUnlike(tweetListItem, position)
                }
                holder.binding.baseTweet.retweetButton.setOnClickListener {
                    postRetweetUnretweet(tweetListItem, position)
                }
                holder.binding.baseTweet.stampListOpened = false
                holder.binding.baseTweet.stampButton.setOnClickListener {
                    val currentStatus =
                        holder.binding.baseTweet.stampListOpened ?: return@setOnClickListener
                    if (!currentStatus) {
                        holder.binding.baseTweet.stampList.layoutManager =
                            LinearLayoutManager(
                                holder.binding.root.context,
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )
                        val adapter =
                            DaggerTweetRecyclerAdapter_StampListAdapterFactory.create().make()
                        holder.binding.baseTweet.stampList.adapter = adapter
                        (holder.binding.baseTweet.stampList.adapter as StampListAdapter).inReplyToStatusId =
                            tweetList[position].tweet.id
                        adapter.loadStamps(holder.binding.baseTweet.stampList)
                    }
                    holder.binding.baseTweet.stampListOpened = !currentStatus
                }
                holder.binding.baseTweet.stockButton.setOnClickListener {
                    postStockUnstock(tweetListItem, position, holder.binding.root.context)
                    holder.binding.baseTweet.belongToGallery = holder.binding.belongToGallery
                }
            }
            is TimelineListItem.TwoImageTweet -> {
                val thisTweet = tweetListItem.tweet
                (holder.binding as TweetRowImage2Binding).tweet = thisTweet
                holder.binding.belongToGallery =
                    belongToGallery(thisTweet, holder.binding.root.context)
                holder.binding.baseTweet.favButton.setOnClickListener {
                    postLikeUnlike(tweetListItem, position)
                }
                holder.binding.baseTweet.stampListOpened = false
                holder.binding.baseTweet.retweetButton.setOnClickListener {
                    postRetweetUnretweet(tweetListItem, position)
                }
                holder.binding.baseTweet.stampButton.setOnClickListener {
                    val currentStatus =
                        holder.binding.baseTweet.stampListOpened ?: return@setOnClickListener
                    if (!currentStatus) {
                        holder.binding.baseTweet.stampList.layoutManager =
                            LinearLayoutManager(
                                holder.binding.root.context,
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )
                        val adapter =
                            DaggerTweetRecyclerAdapter_StampListAdapterFactory.create().make()
                        holder.binding.baseTweet.stampList.adapter = adapter
                        (holder.binding.baseTweet.stampList.adapter as StampListAdapter).inReplyToStatusId =
                            tweetList[position].tweet.id
                        adapter.loadStamps(holder.binding.baseTweet.stampList)
                    }
                    holder.binding.baseTweet.stampListOpened = !currentStatus
                }
                holder.binding.baseTweet.stockButton.setOnClickListener {
                    postStockUnstock(tweetListItem, position, holder.binding.root.context)
                    holder.binding.baseTweet.belongToGallery = holder.binding.belongToGallery
                }

            }
            is TimelineListItem.ThreeImageTweet -> {
                val thisTweet = tweetListItem.tweet
                (holder.binding as TweetRowImage3Binding).tweet = thisTweet
                holder.binding.belongToGallery =
                    belongToGallery(thisTweet, holder.binding.root.context)
                holder.binding.baseTweet.favButton.setOnClickListener {
                    postLikeUnlike(tweetListItem, position)
                }
                holder.binding.baseTweet.retweetButton.setOnClickListener {
                    postRetweetUnretweet(tweetListItem, position)
                }
                holder.binding.baseTweet.stampListOpened = false
                holder.binding.baseTweet.stampButton.setOnClickListener {
                    val currentStatus =
                        holder.binding.baseTweet.stampListOpened ?: return@setOnClickListener
                    if (!currentStatus) {
                        holder.binding.baseTweet.stampList.layoutManager =
                            LinearLayoutManager(
                                holder.binding.root.context,
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )
                        val adapter =
                            DaggerTweetRecyclerAdapter_StampListAdapterFactory.create().make()
                        holder.binding.baseTweet.stampList.adapter = adapter
                        (holder.binding.baseTweet.stampList.adapter as StampListAdapter).inReplyToStatusId =
                            tweetList[position].tweet.id
                        adapter.loadStamps(holder.binding.baseTweet.stampList)
                    }
                    holder.binding.baseTweet.stampListOpened = !currentStatus
                }
                holder.binding.baseTweet.stockButton.setOnClickListener {
                    postStockUnstock(tweetListItem, position, holder.binding.root.context)
                    holder.binding.baseTweet.belongToGallery = holder.binding.belongToGallery
                }
            }
            is TimelineListItem.FourImageTweet -> {
                val thisTweet = tweetListItem.tweet
                (holder.binding as TweetRowImage4Binding).tweet = thisTweet
                holder.binding.belongToGallery =
                    belongToGallery(thisTweet, holder.binding.root.context)
                holder.binding.baseTweet.favButton.setOnClickListener {
                    postLikeUnlike(tweetListItem, position)
                }
                holder.binding.baseTweet.retweetButton.setOnClickListener {
                    postRetweetUnretweet(tweetListItem, position)
                }
                holder.binding.baseTweet.stampListOpened = false
                holder.binding.baseTweet.stampButton.setOnClickListener {
                    val currentStatus =
                        holder.binding.baseTweet.stampListOpened ?: return@setOnClickListener
                    if (!currentStatus) {
                        holder.binding.baseTweet.stampList.layoutManager =
                            LinearLayoutManager(
                                holder.binding.root.context,
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )
                        val adapter =
                            DaggerTweetRecyclerAdapter_StampListAdapterFactory.create().make()
                        holder.binding.baseTweet.stampList.adapter = adapter
                        (holder.binding.baseTweet.stampList.adapter as StampListAdapter).inReplyToStatusId =
                            tweetList[position].tweet.id
                        adapter.loadStamps(holder.binding.baseTweet.stampList)
                    }
                    holder.binding.baseTweet.stampListOpened = !currentStatus
                }
                holder.binding.baseTweet.stockButton.setOnClickListener {
                    postStockUnstock(tweetListItem, position, holder.binding.baseTweet.root.context)
                    holder.binding.baseTweet.belongToGallery = holder.binding.belongToGallery
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

    @Singleton
    @Component(modules = [RepositoryModule::class])
    interface StampListAdapterFactory {
        fun make(): StampListAdapter
    }
}