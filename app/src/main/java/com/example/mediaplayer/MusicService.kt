package com.example.mediaplayer

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder

class MusicService:Service() {

    private var myBinder= MyBinDER()
    var mediaPlayer:MediaPlayer?=null
    override fun onBind(intent: Intent?): IBinder? {
        return myBinder
    }

    inner class MyBinDER:Binder(){
        fun currentService():MusicService{
            return this@MusicService
        }
    }
}