package com.example.mediaplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mediaplayer.databinding.ActivityFavouriteactivityBinding

class favouriteactivity : AppCompatActivity() {

    lateinit var binding: ActivityFavouriteactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityFavouriteactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnback.setOnClickListener {
            finish()
        }


    }


}