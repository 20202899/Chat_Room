package br.com.carlosiwakura.Chat_Room_Android.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import br.com.carlosiwakura.Chat_Room_Android.models.Message
import br.com.carlosiwakura.Chat_Room_Android.R
import com.google.android.material.shape.*

class ChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data = arrayListOf<Message>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.chat_item_list, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHolder) {
            val m = data[position]
            holder.text1.text = m.author
            holder.text2.text = m.message
//            setShape(holder.itemView)
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text1 = itemView.findViewById<TextView>(R.id.text1)
        val text2 = itemView.findViewById<TextView>(R.id.text2)
    }

    private fun setShape(view: View) {
        val shapePathModel = ShapePathModel().apply {
            setAllCorners(CutCornerTreatment(10f))
            setAllEdges(TriangleEdgeTreatment(10f, true))
        }

        val backgroundDrawable = MaterialShapeDrawable(shapePathModel).apply {
            setTint(ContextCompat.getColor(view.context,
                R.color.colorPrimary
            ))
            paintStyle = Paint.Style.FILL
        }

        view.background = backgroundDrawable
    }

}