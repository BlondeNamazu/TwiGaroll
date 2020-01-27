package com.example.twigaroll

import androidx.lifecycle.ViewModelProvider
import com.example.twigaroll.home.gallery.GalleryViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindGalleryViewModelFactory(factory: GalleryViewModelFactory):
            ViewModelProvider.Factory
}