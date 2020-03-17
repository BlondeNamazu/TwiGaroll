package com.example.twigaroll

import com.example.twigaroll.home.HomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ActivityScoped
    @ContributesAndroidInjector(
        modules = [
            GalleryModule::class,
            TimelineModule::class
        ]
    )
    internal abstract fun homeActivity(): HomeActivity
}