package com.gapps.infoapp

import android.app.DownloadManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*

class MainActivity : AppCompatActivity() {

    private lateinit var rvPosts: RecyclerView
    private lateinit var postArrayList: ArrayList<Posts>
    private lateinit var postAdapter: PostAdapter
    private lateinit var dB: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvPosts = findViewById(R.id.rvPosts)
        rvPosts.layoutManager = LinearLayoutManager(this)
        rvPosts.setHasFixedSize(true)

        postArrayList = arrayListOf()

        postAdapter = PostAdapter(this, postArrayList)

        rvPosts.adapter = postAdapter

        EventChangeListener()
    }

    private fun EventChangeListener() {
        dB = FirebaseFirestore.getInstance()
        dB.collection("Posts").orderBy("relativeTime", Query.Direction.DESCENDING)
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        Log.e("Firestore Error", error.message.toString())
                        return
                    }
                    for (dc: DocumentChange in value?.documentChanges!!) {
                        if (dc.type == DocumentChange.Type.ADDED) {
                            postArrayList.add(dc.document.toObject(Posts::class.java))
                        }
                    }
                    postAdapter.notifyDataSetChanged()
                }
            })
    }
}