package br.com.carlosiwakura.Chat_Room_Android.activities

import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentStatePagerAdapter
import br.com.carlosiwakura.Chat_Room_Android.R
import br.com.carlosiwakura.Chat_Room_Android.adapters.CustomFragmentAdapter
import br.com.carlosiwakura.Chat_Room_Android.fragments.ChatsFragment
import br.com.carlosiwakura.Chat_Room_Android.interfaces.ObservableChat
import br.com.carlosiwakura.Chat_Room_Android.models.Message
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    val mSocketChat: Socket = IO.socket("http://192.168.1.106:3000/")
    private val mHandler = Handler()
    private val mGson = Gson()
    var mRoomName = ""
    var mName = ""
    private val mObservableChat = ObservableChat()
    private lateinit var mFragmentAdapter: CustomFragmentAdapter
    companion object {
        var sInstance: MainActivity? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        sInstance = this



        mFragmentAdapter = CustomFragmentAdapter(supportFragmentManager)
        mFragmentAdapter.fragments.add(ChatsFragment.newInstance().apply {
            mObservableChat.addObserver(this)
        })
        viewpager.adapter = mFragmentAdapter

        mFragmentAdapter.notifyDataSetChanged()

        mSocketChat.on("result_chat") {
            val type = object : TypeToken<Array<String>>() {}.type
            val d = it[0]
            val a = mGson.fromJson<Array<String>>(d.toString(), type)
            mObservableChat.mData = a.toMutableList()
        }

        mSocketChat.on(Socket.EVENT_CONNECT) {
            mHandler.post { Snackbar.make(toolbar, "Conectado", Snackbar.LENGTH_LONG).show() }
        }

        mSocketChat.on(Socket.EVENT_CONNECT_ERROR) {
            Log.d("%s", "CONNECTED")
        }

        mSocketChat.on(Socket.EVENT_ERROR) {
            Log.d("%s", "CONNECTED")
        }

        mSocketChat.on(Socket.EVENT_DISCONNECT) {
            Log.d("%s", "CONNECTED")
        }

        mSocketChat.connect()
        alertName()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    private fun alertName() {
        val input = EditText(this)
        AlertDialog.Builder(this)
            .setView(input)
            .setTitle("Entre com seu nome")
            .setPositiveButton(android.R.string.ok) {a, b ->
                mName = input.text.toString()
            }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }

    fun addObserver(observer: Observer) {
        mObservableChat.addObserver(observer)
    }
}
