package com.cedarsstudio.internal.schoollogging.utils.di

import com.cedarsstudio.internal.schoollogging.remote.auth.repos.Auth
import com.cedarsstudio.internal.schoollogging.remote.students.StudentLists
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object Singletons

@Module
@InstallIn(ViewModelComponent::class)
object ViewModels {

    @Provides
    @ViewModelScoped
    fun provideAuth(): Auth = Auth()

    @Provides
    @ViewModelScoped
    fun provideStudentsDB(): StudentLists = StudentLists()

}