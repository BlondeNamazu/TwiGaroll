package com.example.twigaroll

import androidx.lifecycle.ViewModel
import com.example.twigaroll.home.gallery.GalleryViewModel
import com.example.twigaroll.home.timeline.TimelineViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class TimelineModule {
    @Binds
    @IntoMap
    @ViewModelKey(TimelineViewModel::class)
    abstract fun bindTimelineViewModel(viewModel: TimelineViewModel): ViewModel
}