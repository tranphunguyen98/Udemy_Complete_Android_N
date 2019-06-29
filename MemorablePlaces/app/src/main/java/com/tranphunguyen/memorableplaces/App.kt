package com.tranphunguyen.memorableplaces

import android.app.Application

class App : Application() {

    companion object {
        private lateinit var self: App
        fun self(): App {
            return self
        }
    }

    override fun onCreate() {
        super.onCreate()
        self = this
    }
}