package com.example.mediaplayer

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.mediaplayer.databinding.ItemRecyclarBinding

class music(private val context: Context,private val musiclist:ArrayList<dataClass>): RecyclerView.Adapter<music.MyHolder>() {
     class MyHolder(binding: ItemRecyclarBinding): RecyclerView.ViewHolder(binding.root) {

         val title=binding.tvsong
         val image=binding.mvid
         val album=binding.tvalbum
         val duration=binding.tvduration
         val root=binding.root


     }

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
         return MyHolder(ItemRecyclarBinding.inflate(LayoutInflater.from(context),parent,false))
     }

     override fun onBindViewHolder(holder: MyHolder, position: Int) {
         holder.title.text=musiclist[position].title
         holder.album.text=musiclist[position].album
         holder.duration.text= formatDuration(musiclist[position].duration)
      /*   Glide.with(context).load(musiclist[position].artUri).apply(RequestOptions()
             .placeholder(R.drawable.logoworld).centerCrop())
             .into(holder.image)*/
         holder.root.setOnClickListener {
             Intent(context,PlayerActivity::class.java).also {
             it.putExtra("index",position)
             it.putExtra("class","music")
                 startActivity(context,it,null)
             }
         }
     }

     override fun getItemCount(): Int {
         return musiclist.size
     }
 }