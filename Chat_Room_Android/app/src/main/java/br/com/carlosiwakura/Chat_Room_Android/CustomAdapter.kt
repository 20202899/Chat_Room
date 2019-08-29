package br.com.carlosiwakura.Chat_Room_Android

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data = arrayListOf<String>()
    private var lastPostitionAnimation = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_list, parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHolder) {
            val text = data[position]
            holder.text1.text = text
            holder.itemView.setOnClickListener(null)
            holder.itemView.setOnClickListener {
                val context = it.context
                val intent = Intent(context, ChatActivity::class.java)
                intent.putExtra("key", data[position])
                context.startActivity(intent)
            }
            inAnim(holder.itemView, position)
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text1 = itemView.findViewById<TextView>(R.id.text1)
    }

    private fun inAnim(view: View, position: Int) {
        if (position > lastPostitionAnimation) {
            view.startAnimation(AnimationUtils.loadAnimation(view.context, android.R.anim.slide_in_left))
            lastPostitionAnimation = position
        }
    }

}