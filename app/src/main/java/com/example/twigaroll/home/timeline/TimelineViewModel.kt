package com.example.twigaroll.home.timeline

import android.view.View
import androidx.lifecycle.*
import com.example.twigaroll.R
import com.example.twigaroll.home.gallery.GalleryNavigator
import com.example.twigaroll.home.post_tweet.PostTweetFragment
import com.example.twigaroll.util.TweetRequestRepository
import com.twitter.sdk.android.core.models.Tweet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TimelineViewModel @Inject constructor(
    private val tweetRequestRepository: TweetRequestRepository
) : ViewModel() {

    private lateinit var navigator: GalleryNavigator
    private val _tweetList = MutableLiveData<List<Tweet>>()
    val tweetList: LiveData<List<Tweet>>
        get() = _tweetList

    private val _shouldBackToTimelineTop = MutableLiveData<Boolean>()
    val shouldBackToTimelineTop: LiveData<Boolean>
        get() = _shouldBackToTimelineTop

    fun refreshTimeline(v: View) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _tweetList.postValue(tweetRequestRepository.getTimeLine())
            }
        }
    }

    fun toggleShouldBackToTimelineTop(should: Boolean) = _shouldBackToTimelineTop.postValue(should)

    fun setNavigator(navigator: GalleryNavigator) {
        this.navigator = navigator
    }

    fun beginPostTweetFragment() {
        if (!this::navigator.isInitialized) return
        val postTweetFragment = PostTweetFragment()
        navigator.addFragmentToActivity(R.id.timeline_container, postTweetFragment)
    }

    fun postTwewt(text: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                tweetRequestRepository.postTweet(text)
            }
        }
    }
}