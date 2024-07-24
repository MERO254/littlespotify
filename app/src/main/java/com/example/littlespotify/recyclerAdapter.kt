package com.example.littlespotify

import android.app.AlertDialog
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView

class recyclerAdapter(var context: Context, var list: MutableList<music>) : RecyclerView.Adapter<recyclerAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var artist: TextView = view.findViewById(R.id.txtartist)
        var musicname: TextView = view.findViewById(R.id.txtmusicname)
        var menu: ImageView = view.findViewById(R.id.toolbar2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recyclerviewlayout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.artist.text = list[position].artist
        holder.musicname.text = list[position].song

        holder.itemView.setOnClickListener {
            val view = LayoutInflater.from(context).inflate(R.layout.musiclayout, null)

            val alertDialog = AlertDialog.Builder(context)
                .setTitle("Playing Music")
                .setView(view)
                .setOnDismissListener {
                    mediaPlayer?.release()
                    mediaPlayer = null
                }
                .create()

            view.findViewById<ImageButton>(R.id.imgplay).setImageResource(R.drawable.pause)


            view.findViewById<ImageButton>(R.id.imgplay).setOnClickListener {
                try {
                    mediaPlayer?.release() // Release any existing MediaPlayer instance
                    mediaPlayer = MediaPlayer().apply {
                        setDataSource(list[position].audio)
                        prepareAsync()
                        setOnPreparedListener {
                            it.start()
                            Toast.makeText(context, "Playing audio", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(context, "Error playing audio", Toast.LENGTH_SHORT).show()
                }
                if (mediaPlayer?.isPlaying == true) {
                    view.findViewById<ImageButton>(R.id.imgplay).setImageResource(R.drawable.pause)
                }else{
                    view.findViewById<ImageButton>(R.id.imgplay).setImageResource(R.drawable.play)
                }
            }

            view.findViewById<TextView>(R.id.txtsongname).text = "${list[position].song}: ${list[position].artist}"

            view.findViewById<ImageView>(R.id.imgforward).setOnClickListener {
                if (position < list.size - 1) {
                    playAudio(list[position + 1])
                    view.findViewById<TextView>(R.id.txtsongname).text = "${list[position + 1].song}: ${list[position + 1].artist}"
                }
            }

            view.findViewById<ImageView>(R.id.imgback).setOnClickListener {
                if (position > 0) {
                    playAudio(list[position - 1])
                    view.findViewById<TextView>(R.id.txtsongname).text = "${list[position - 1].song}: ${list[position - 1].artist}"
                }
            }

            alertDialog.show()
        }

        holder.menu.setOnClickListener{
            popmenu(holder.menu,position)
        }
    }

    private var mediaPlayer: MediaPlayer? = null

    private fun playAudio(music: music) {
        try {
            mediaPlayer?.release()
            mediaPlayer = MediaPlayer().apply {
                setDataSource(music.audio)
                prepareAsync()
                setOnPreparedListener {
                    it.start()
                    Toast.makeText(context, "Playing audio", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Error playing audio", Toast.LENGTH_SHORT).show()
        }
    }

    private fun popmenu(view: View,position:Int){
        var popmenu = PopupMenu(context,view)
        popmenu.inflate(R.menu.recyclermenu)

        popmenu.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.share ->{
                    val shareIntent = Intent().apply{
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, "chech out this music:${list[position].song}: ${list[position].artist}")
                        type = "text/plain"
                    }
                    context.startActivity(Intent.createChooser(shareIntent,"share via"))
                    return@setOnMenuItemClickListener true
                }
                R.id.download ->{
                    val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

                    var request = DownloadManager.Request(Uri.parse(list[position].audio))
                        .setTitle(list[position].song)
                        .setDescription("Downloading")
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                        .setAllowedOverMetered(true)
                        .setAllowedOverRoaming(true)
                        .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "filename.ext")
                    downloadManager.enqueue(request)
                    return@setOnMenuItemClickListener true
                }
                R.id.delet ->{
                    list.removeAt(position)
                    notifyDataSetChanged()
                    return@setOnMenuItemClickListener true
                }

                else -> return@setOnMenuItemClickListener true
            }
        }
        popmenu.show()
    }
}
