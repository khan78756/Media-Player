package com.example.mediaplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mediaplayer.databinding.ActivityFavouriteactivityBinding
import com.example.mediaplayer.databinding.ActivityMainBinding
import com.example.mediaplayer.databinding.ActivityPlaylistactivityBinding

class playlistactivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlaylistactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityPlaylistactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.btnback.setOnClickListener {
            finish()
        }


    }
}