package br.com.carlosiwakura.Chat_Room_Android.fragments

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.carlosiwakura.Chat_Room_Android.adapters.CustomAdapter
import br.com.carlosiwakura.Chat_Room_Android.R
import br.com.carlosiwakura.Chat_Room_Android.activities.MainActivity
import br.com.carlosiwakura.Chat_Room_Android.models.Message
import kotlinx.android.synthetic.main.fragment_chats.*
import java.util.*
import kotlin.collections.ArrayList

class ChatsFragment : Fragment(), Observer {


    private val mHandler = Handler()
    private val mAdapter = CustomAdapter()

    companion object {
        fun newInstance() = ChatsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_chats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerview()

        fab.setOnClickListener { view ->
            alertImput()
        }
    }

    override fun update(p0: Observable?, p1: Any?) {
        val list = p1 as MutableList<String>?

        if (list != null) {
            mAdapter.data.clear()
            mAdapter.data.addAll(list)
            mHandler.post { mAdapter.notifyDataSetChanged() }
        }
    }

    private fun initRecyclerview() {
        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )
        recyclerview.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        recyclerview.adapter = mAdapter
    }

    private fun alertImput() {
        val mainActivity = (activity as MainActivity)
        val input = EditText(context)
        AlertDialog.Builder(mainActivity)
            .setView(input)
            .setTitle("Nome do chat")
            .setPositiveButton(android.R.string.ok) {a, b ->
                if (mainActivity.mSocketChat.connected()) {
                    val value = input.text.toString()
                    mainActivity.mRoomName = value
                    mainActivity.mSocketChat.emit("create-chat", value)
                }
            }
            .setNegativeButton(android.R.string.cancel, null).show()
    }
}