package com.gapps.infoapp

import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PostAdapter(private val context: Context, private val postList: ArrayList<Posts>): RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_post,parent,false)
        return PostViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post: Posts = postList[position]
        holder.heading.text = post.heading
        Glide.with(context).load(post.imgPost).into(holder.imgPost)
        holder.description.text = post.description
        holder.relativeTime.text = DateUtils.getRelativeTimeSpanString(post.relativeTime!!)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    public class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val heading: TextView = itemView.findViewById(R.id.tvHeading)
        val imgPost: ImageView = itemView.findViewById(R.id.ivPost)
        val description: TextView = itemView.findViewById(R.id.tvDescription)
        val relativeTime: TextView = itemView.findViewById(R.id.tvRelativeTime)
    }

}