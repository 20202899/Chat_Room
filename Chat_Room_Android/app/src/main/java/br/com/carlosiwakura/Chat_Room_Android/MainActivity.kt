package br.com.carlosiwakura.Chat_Room_Android

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    val mSocketChat: Socket = IO.socket("http://192.168.1.106:3000/")
    private val mHandler = Handler()
    private val mAdapter = CustomAdapter()
    private val mGson = Gson()
    private var mRoomName = ""
    companion object {
        var sInstance: MainActivity? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        sInstance = this

        fab.setOnClickListener { view ->
            alertImput()
        }

        mSocketChat.on("result_chat") {
            val type = object : TypeToken<Array<String>>() {}.type
            val d = it[0]
            val a = mGson.fromJson<Array<String>>(d.toString(), type)
            mAdapter.data.clear()
            mAdapter.data.addAll(a.toList())
            mHandler.post { mAdapter.notifyDataSetChanged() }

            if (mAdapter.data.size > 0) {
                val roomCreated = mAdapter.data.last()
                if (mRoomName == roomCreated) {
                    val intent = Intent(this@MainActivity, ChatActivity::class.java)
                    intent.putExtra("key", roomCreated)
                    startActivity(intent)
                    mRoomName = ""
                }
            }
        }

        mSocketChat.on(Socket.EVENT_CONNECT) {
            mHandler.post { Snackbar.make(fab, "Conectado", Snackbar.LENGTH_LONG).show() }
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

        initRecyclerview()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item)
    }

    private fun initRecyclerview() {
        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL, false
        )
        recyclerview.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerview.adapter = mAdapter
    }

    private fun alertImput() {
        val input = EditText(this)
        AlertDialog.Builder(this)
            .setView(input)
            .setTitle("Nome do chat")
            .setPositiveButton(android.R.string.ok) {a, b ->
                if (mSocketChat.connected()) {
                    val value = input.text.toString()
                    mRoomName = value
                    mSocketChat.emit("create-chat", value)
                }
            }
            .setNegativeButton(android.R.string.cancel, null).show()
    }
}
