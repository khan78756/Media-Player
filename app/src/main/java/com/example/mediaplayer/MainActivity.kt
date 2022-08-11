package com.example.mediaplayer

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mediaplayer.databinding.ActivityMainBinding
import java.io.File
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle:ActionBarDrawerToggle
    private lateinit var adapter:music

    companion object{
       lateinit var musicListMP: ArrayList<dataClass>
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //request permission
        requestPermission()
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //function for recycler view
        initial()


    //============================================================================================//

        //for drawer purpose
        toggle= ActionBarDrawerToggle(this,binding.root,R.string.open,R.string.close)
        binding.root.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.totalsongs.text="Total Songs : "+ adapter.itemCount



        //======================================================================================//


        binding.btnshuffle.setOnClickListener {
            Intent(this,PlayerActivity::class.java).also {
                it.putExtra("index",0)
                it.putExtra("class","MainActivity")
                startActivity(it)
            }
        }



        //===================================================================================//

        binding.btnfav.setOnClickListener {
            Intent(this,favouriteactivity::class.java).also {
                startActivity(it)
            }
        }



        //===================================================================================//





        binding.btnplaylist.setOnClickListener {
            Intent(this,playlistactivity::class.java).also {
                startActivity(it)
            }
        }


        //====================================================================================//



        binding.navid.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.item1-> Toast.makeText(applicationContext,"You press Feedback",Toast.LENGTH_SHORT).show()
                R.id.item2-> Toast.makeText(applicationContext,"You press settings",Toast.LENGTH_SHORT).show()
                R.id.item3-> Toast.makeText(applicationContext,"You press About",Toast.LENGTH_SHORT).show()
                R.id.item4-> exitProcess(1)
            }
            true
        }
    }


    //=======================================================================================//

    //function for permission
    private fun requestPermission(){
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
               13
            )

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show()
           initial()

        }
        else{
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 13)
        }
    }



    //======================================================================================//

    //for drawer function
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item))
            return true
        return super.onOptionsItemSelected(item)
    }


    //============================================================================================//


    @SuppressLint("Range")
    private fun getAllAudio() : ArrayList<dataClass>{
        val templist=ArrayList<dataClass>()
        val selection=MediaStore.Audio.Media.IS_MUSIC + "!= 0"
        val projection= arrayOf(MediaStore.Audio.Media._ID,MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ALBUM,MediaStore.Audio.Media.ARTIST,MediaStore.Audio.Media.DURATION,MediaStore.Audio.Media.DATE_ADDED,
            MediaStore.Audio.Media.DATA,MediaStore.Audio.Media.ALBUM_ID)



        val cursor=this.contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,projection,selection,
        null,MediaStore.Audio.Media.DATE_ADDED ,null)

        println("Hello")

        if (cursor != null) {
            if (cursor.moveToFirst())
                do {
                    val titleC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                    val idC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID))
                    val artistC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                    val albumC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                    val pathC = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                    val durationC = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
                    val albumidC = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)).toString()
                    val uri= Uri.parse("content://media/external/audio/albumart")
                    val artUriC=Uri.withAppendedPath(uri, albumidC).toString()
                    val music = dataClass(
                        title = titleC, id = idC, artist = artistC, album = albumC, path = pathC,
                        duration = durationC, artUri = artUriC
                    )
                    val file = File(music.path)
                    templist.add(music)

                } while (cursor.moveToNext())
            cursor.close()
        }
        return templist
    }




    //Definition of recycler view function
    private fun initial(){
        musicListMP=getAllAudio()
        binding.musicRV.setHasFixedSize(true)
        binding.musicRV.setItemViewCacheSize(13)
        val layoutManager = LinearLayoutManager(applicationContext)
        layoutManager.reverseLayout =true
        layoutManager.stackFromEnd =true

        // binding.musicRV.layoutManager=LinearLayoutManager(this)
        adapter= music(this, musicListMP)
        binding.musicRV.adapter=adapter
        binding.musicRV.layoutManager=layoutManager
    }
}