package com.example.twigaroll.home.timeline

import android.view.View
import androidx.lifecycle.*
import com.example.twigaroll.util.TweetRequestRepository
import com.twitter.sdk.android.core.models.Tweet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TimelineViewModel @Inject constructor(
    private val tweetRequestRepository: TweetRequestRepository
) : ViewModel() {

    private val _tweetList = MutableLiveData<List<Tweet>>()
    val tweetList: LiveData<List<Tweet>>
        get() = _tweetList

    fun refreshTimeline(v: View) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _tweetList.postValue(tweetRequestRepository.getTimeLine())
            }
        }
    }
}