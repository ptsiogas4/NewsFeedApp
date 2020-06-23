package com.example.newsfeedapp.common

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject

class NetworkHandler @Inject constructor (private val context: Context) :
    INetworkAwareHandler {

    override fun isOnline(): Boolean {

        val connectivityManager=context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkInfo=connectivityManager.activeNetworkInfo

        return  networkInfo!=null && networkInfo.isConnected
    }
}