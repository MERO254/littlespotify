package com.example.littlespotify

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject


class MusicFragment : Fragment() {

    private lateinit var dbFirebase:FirebaseFirestore
    private lateinit var list:MutableList<music>
    private lateinit var adapter: recyclerAdapter
    private lateinit var scrollviewlist:MutableList<podcast>
    private lateinit var scrollAdapter: scrollviewAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_music, container, false)
        list = mutableListOf<music>()
        scrollviewlist = mutableListOf<podcast>()



        var recyclerView:RecyclerView = view.findViewById(R.id.musicrecycler)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = recyclerAdapter(requireContext(),list)
        recyclerView.adapter = adapter


        loadmusic()

        var scrollrecycler:RecyclerView = view.findViewById(R.id.scrollrecycler)
        scrollAdapter = scrollviewAdapter(requireContext(),scrollviewlist)
        scrollrecycler.layoutManager = GridLayoutManager(requireContext(),3)
        scrollAdapter = scrollviewAdapter(requireContext(),scrollviewlist)
        scrollrecycler.adapter = scrollAdapter


        scrollviewdata()



        return view
    }

    private fun loadmusic(){
        list.clear()
        dbFirebase = FirebaseFirestore.getInstance()
        dbFirebase.collection("audio").get().addOnSuccessListener {
            if(it != null){
                for(i in it){
                    var value = i.toObject(music::class.java)
                    list.add(value)
                    adapter.notifyDataSetChanged()
                }
            }
        }
            .addOnFailureListener{
                Toast.makeText(requireContext(), "no values found", Toast.LENGTH_SHORT).show()
            }
    }
    private fun scrollviewdata(){
        scrollviewlist.clear()
        dbFirebase = FirebaseFirestore.getInstance()
        dbFirebase.collection("podcast").get().addOnSuccessListener {
            for(i in it){
                var value = i.toObject(podcast::class.java)
                scrollviewlist.add(value)
            }
            scrollAdapter.notifyDataSetChanged()

        }
    }


}