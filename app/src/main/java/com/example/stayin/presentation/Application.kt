package com.example.stayin.presentation

import android.app.Application
import com.example.stayin.di.AppModule
import dagger.Provides
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

// all ways remember to add the name attr in the application tag in the manifests tag.
//"when ever" you add a application file like this

@HiltAndroidApp
class Application @Inject constructor() : Application()