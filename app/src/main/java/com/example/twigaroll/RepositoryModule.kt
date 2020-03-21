package com.example.twigaroll

import com.example.twigaroll.util.FileIORepository
import com.example.twigaroll.util.FileIORepositoryImpl
import com.example.twigaroll.util.TweetRequestRepository
import com.example.twigaroll.util.TweetRequestRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {
    @Binds
    abstract internal fun bindsFileIORepository(repository: FileIORepositoryImpl): FileIORepository

    @Binds
    abstract internal fun bindsTweetRequestRepository(repository: TweetRequestRepositoryImpl): TweetRequestRepository
}