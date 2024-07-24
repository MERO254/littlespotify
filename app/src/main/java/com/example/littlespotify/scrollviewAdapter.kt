package com.example.littlespotify

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class scrollviewAdapter(var context: Context, var list:List<podcast>):RecyclerView.Adapter<scrollviewAdapter.ViewHolder>(){
    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        var image:ImageView = view.findViewById(R.id.imgscroll)
        var description:TextView = view.findViewById(R.id.txtpodcast)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.scrollviewlayout,parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var data = list[position]

        Picasso.get()
            .load(list[position].image)
            .into(holder.image)

        holder.description.text = data.description

        holder.itemView.setOnClickListener{
            var view = LayoutInflater.from(context).inflate(R.layout.musiclayout,null)
            var alertDialog = AlertDialog.Builder(context)
                .setView(view)
                .setTitle("podcast")
                .create()

            var image = view.findViewById<ImageView>(R.id.imageView)
            Picasso.get()
                .load(list[position].image)
                .into(image)

            view.findViewById<TextView>(R.id.txtsongname).text = list[position].description
            alertDialog.show()
        }

    }


}