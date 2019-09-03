package br.com.carlosiwakura.chat_room_androidv2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.carlosiwakura.chat_room_androidv2.R
import br.com.carlosiwakura.chat_room_androidv2.activities.MainActivity
import br.com.carlosiwakura.chat_room_androidv2.views.CustomFab
import kotlinx.android.synthetic.main.activity_main.*

class ChatFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context).inflate(R.layout.chat_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = "Chat"
        activity?.fab?.typeNavigation = CustomFab.TypeNavigation.ChatType
        activity?.fab?.setImageResource(R.drawable.ic_send_white_24dp)
        activity?.let {
            (it as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }
}