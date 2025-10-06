package com.route.newsappc42gsunwed.utils

import android.content.Context
import android.net.ConnectivityManager

object NetworkUtils {
    fun isInternetConnected(
        context: Context
    ): Boolean {
        val connectivityManager = context.getSystemService(ConnectivityManager::class.java);

        if (connectivityManager != null) {
            val activeNetwork = connectivityManager.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting
        }
        return false;
    }
}