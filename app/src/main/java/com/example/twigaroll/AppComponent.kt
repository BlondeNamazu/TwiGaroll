package com.example.twigaroll

import com.example.twigaroll.home.timeline.RepositoryModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityModule::class,
        ViewModelModule::class,
        RepositoryModule::class
    ]
)
interface AppComponent : AndroidInjector<TwiGarollApplication> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<TwiGarollApplication>()
}