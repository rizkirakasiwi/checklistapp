package com.example.checklistbtsid.ui.screens

import com.example.checklistbtsid.ui.screens.login.LoginServiceImpl
import com.example.checklistbtsid.ui.screens.login.LoginServies
import com.example.checklistbtsid.ui.screens.main.MainServiceImpl
import com.example.checklistbtsid.ui.screens.main.MainServices
import com.example.checklistbtsid.ui.screens.registration.RegistrationService
import com.example.checklistbtsid.ui.screens.registration.RegistrationServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BindServices {
    @Binds
    @Singleton
    abstract fun bindRegistration(registrationServiceImpl: RegistrationServiceImpl): RegistrationService

    @Binds
    @Singleton
    abstract fun bindLogin(loginServiceImpl: LoginServiceImpl):LoginServies

    @Binds
    @Singleton
    abstract fun bindMain(mainServiceImpl: MainServiceImpl):MainServices
}