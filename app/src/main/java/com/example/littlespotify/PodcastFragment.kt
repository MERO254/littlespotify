package com.example.littlespotify

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class PodcastFragment : Fragment() {

    private lateinit var adapter: scrollviewAdapter
    private lateinit var list: MutableList<podcast>
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_podcast, container, false)

        list = mutableListOf()

        recyclerView = view.findViewById(R.id.podcastrecycler)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = scrollviewAdapter(requireContext(),list)
        recyclerView.adapter = adapter

        getpodcast()

        return view
    }
    private fun getpodcast(){
        var dbfirestore = FirebaseFirestore.getInstance()
        dbfirestore.collection("podcast").get().addOnSuccessListener {
            for(i in it){
                if(i != null){
                    var value = i.toObject(podcast::class.java)
                    list.add(value)
                }else{
                    Toast.makeText(requireContext(), "no podcasts found", Toast.LENGTH_SHORT).show()
                }
                adapter.notifyDataSetChanged()
            }
        }
    }


}