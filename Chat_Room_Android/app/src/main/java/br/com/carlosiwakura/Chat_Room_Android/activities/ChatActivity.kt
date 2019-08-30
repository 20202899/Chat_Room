package br.com.carlosiwakura.Chat_Room_Android.activities

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.carlosiwakura.Chat_Room_Android.adapters.ChatAdapter
import br.com.carlosiwakura.Chat_Room_Android.models.Message
import br.com.carlosiwakura.Chat_Room_Android.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.content_chat.*
import kotlinx.android.synthetic.main.content_chat.recyclerview

class ChatActivity : AppCompatActivity() {

    private lateinit var mKey: String
    private val mGson = Gson()
    private val mHandler = Handler()
    private val mAdapter = ChatAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        setSupportActionBar(toolbar)
        mKey = intent.extras["key"] as String
        title = mKey
        MainActivity.sInstance?.mSocketChat?.on("receive-message") {
            val type = object : TypeToken<Message>(){}.type
            val data = mGson.fromJson<Message>(it[0].toString(), type)
            mHandler.post {
                mAdapter.data.add(data)
                mAdapter.notifyDataSetChanged()
            }
        }?.on("messages-chat") {

        }

        fab.setOnClickListener {
            if ( MainActivity.sInstance?.mSocketChat?.connected() == true) {
                val message = Message(
                    mKey,
                    field1.text.toString(),
                    MainActivity.sInstance?.mName
                )
                MainActivity.sInstance?.mSocketChat?.emit("send-message-chat", mGson.toJson(message))
                field1.setText("")
            }
        }
        initRecyclerview()
    }

    override fun onResume() {
        super.onResume()
        MainActivity.sInstance?.mSocketChat?.emit("join-chat", mKey)
    }

    override fun onPause() {
        super.onPause()
        MainActivity.sInstance?.mSocketChat?.emit("leave-chat", mKey)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun initRecyclerview() {
        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL, false)
        recyclerview.adapter = mAdapter

    }

}
