package com.example.mediaplayer

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import com.example.mediaplayer.databinding.ActivityPlayerBinding
import java.lang.Exception

class PlayerActivity : AppCompatActivity() ,ServiceConnection{

    companion object{
        lateinit var musicListPA:ArrayList<dataClass>
        var  songPosition:Int=0
       // var mediaPlayer:MediaPlayer? =null
        var isPlaying:Boolean=false
        var musicService:MusicService?=null
    }


    private lateinit var binding: ActivityPlayerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent= Intent(this,MusicService::class.java)
        bindService(intent,this, BIND_AUTO_CREATE)
        startService(intent)



        //for inialaization
        initial()


        //===========================================================================//


        binding.btnback.setOnClickListener {
            finish()
        }



        //================================================================================//

        binding.fbplay.setOnClickListener {
            if (isPlaying)
            { pauseMusic()}
            else
            { playMusic()}
        }


        //=========================================================================================

         //for next song
        binding.fbnext.setOnClickListener {
            prevnextSong(true) }
        //for previous song
        binding.fbback.setOnClickListener { prevnextSong(false)  }


    }



    //=================================================================================//




    //function for initalaization
    private fun initial(){
        songPosition=intent.getIntExtra("index",0)
        when(intent.getStringExtra("class")){
            "musicSearch"->{
                musicListPA= ArrayList()
                musicListPA.addAll(MainActivity.musicListsearch)
                layoutset()
            }
            "music"-> {
                musicListPA= ArrayList()
                musicListPA.addAll(MainActivity.musicListMP)
                layoutset()
                createmediaPlayer()


            }
            "MainActivity"->{
                musicListPA= ArrayList()
                musicListPA.addAll(MainActivity.musicListMP)
                musicListPA.shuffle()
                layoutset()
                createmediaPlayer()
            }
        }
    }



   //==================================================================================//

    private fun createmediaPlayer(){
        try {
            if (musicService!!.mediaPlayer==null)
               musicService!!. mediaPlayer= MediaPlayer()
               musicService!!. mediaPlayer!!.reset()
               musicService!!. mediaPlayer!!.setDataSource(musicListPA[songPosition].path)
               musicService!!. mediaPlayer!!.prepare()
               musicService!!. mediaPlayer!!.start()
                isPlaying=true
                binding.fbplay.setImageResource(R.drawable.ic_baseline_pause_24)

            }catch (e:Exception){
            return
        }
    }



    //function for setting song name
    private fun layoutset(){
        binding.tvsongNamePA.text= musicListPA[songPosition].title
    }



    //===========================================================================//




    private fun playMusic(){

        binding.fbplay.setImageResource(R.drawable.ic_baseline_pause_24)
        isPlaying=true
       musicService!!. mediaPlayer!!.start()

    }


    //==================================================================================//


    private fun pauseMusic(){

        binding.fbplay.setImageResource(R.drawable.play)
        isPlaying=false
       musicService!!.mediaPlayer!!.pause()

    }


    //==========================================================================================//


    private fun prevnextSong(increment:Boolean){
        if(increment){
            positioncheck(increment)
            layoutset()
           // createmediaPlayer()


        }
        else{
            positioncheck(increment)
            layoutset()
           // createmediaPlayer()
        }
    }


    private fun positioncheck(increment: Boolean){
        if (increment){
            if (musicListPA.size-1 == songPosition){
                songPosition=0}
            else{
                ++songPosition
            }
        }
        else{
            if (songPosition ==0){
                songPosition= musicListPA.size-1
            }
            else{
                --songPosition
            }
        }

    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        val binder=service as MusicService.MyBinDER
        musicService=binder.currentService()
        createmediaPlayer()

    }

    override fun onServiceDisconnected(name: ComponentName?) {
        musicService=null
    }


    //================================================================================//



}