package com.example.twigaroll

import com.example.twigaroll.util.FileIORepository
import com.example.twigaroll.util.FileIORepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {
    @Binds
    abstract internal fun bindsFileIORepository(repository: FileIORepositoryImpl): FileIORepository
}