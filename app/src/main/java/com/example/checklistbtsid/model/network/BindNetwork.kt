package com.example.checklistbtsid.model.network

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BindNetwork{
    @Binds
    @Singleton
    abstract fun bindAuth(authImpl: AuthImpl):AuthNetwork

    @Binds
    @Singleton
    abstract fun bindCheck(checkListNetworkImpl: CheckListNetworkImpl):CheckListNetwork
}