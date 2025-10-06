package com.route.newsappc42gsunwed

import android.app.Application
import com.route.newsappc42gsunwed.database.NewsAppDatabase

class NewsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        NewsAppDatabase.init(this)
    }
}
