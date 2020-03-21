package com.example.twigaroll.data

import com.twitter.sdk.android.core.models.Tweet

sealed class TimelineListItem {
    abstract val tweet: Tweet

    class NoImageTweet(override val tweet: Tweet) : TimelineListItem()
    class OneImageTweet(override val tweet: Tweet, val belongToGallery: Boolean) :
        TimelineListItem()

    class TwoImageTweet(override val tweet: Tweet, val belongToGallery: Boolean) :
        TimelineListItem()

    class ThreeImageTweet(override val tweet: Tweet, val belongToGallery: Boolean) :
        TimelineListItem()

    class FourImageTweet(override val tweet: Tweet, val belongToGallery: Boolean) :
        TimelineListItem()
}
