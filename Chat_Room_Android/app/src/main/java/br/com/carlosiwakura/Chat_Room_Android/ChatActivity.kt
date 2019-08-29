package br.com.carlosiwakura.Chat_Room_Android

import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson

import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.content_chat.*

class ChatActivity : AppCompatActivity() {

    private lateinit var mKey: String
    private val mGson = Gson()
    private val mHandler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        setSupportActionBar(toolbar)
        mKey = intent.extras["key"] as String
        title = mKey
        MainActivity.sInstance?.mSocketChat?.emit("join-chat", mKey)
        MainActivity.sInstance?.mSocketChat?.on("receive-message") {
            mHandler.post {
                Toast.makeText(this@ChatActivity, it[0].toString(), Toast.LENGTH_LONG)
                    .show()
            }
        }
        MainActivity.sInstance?.mSocketChat?.on("messages-chat") {
//            mHandler.post {
//                Toast.makeText(this@ChatActivity, it[0].toString(), Toast.LENGTH_LONG)
//                    .show()
//            }
        }
        fab.setOnClickListener {
            if ( MainActivity.sInstance?.mSocketChat?.connected() == true) {
                val message = Message(mKey, field1.text.toString())
                MainActivity.sInstance?.mSocketChat?.emit("send-message-chat", mGson.toJson(message))
                field1.setText("")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        MainActivity.sInstance?.mSocketChat?.emit("leave-chat", mKey)
    }

}
