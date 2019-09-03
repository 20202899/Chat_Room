package br.com.carlosiwakura.chat_room_androidv2.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.transition.TransitionInflater
import br.com.carlosiwakura.chat_room_androidv2.R
import br.com.carlosiwakura.chat_room_androidv2.activities.MainActivity
import br.com.carlosiwakura.chat_room_androidv2.databinding.EnterNameFragmentBinding
import br.com.carlosiwakura.chat_room_androidv2.models.User
import br.com.carlosiwakura.chat_room_androidv2.views.CustomFab
import kotlinx.android.synthetic.main.activity_main.*

class EnterNameFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context).inflate(
            R.layout.enter_name_fragment, container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exitTransition = TransitionInflater.from(context)
            .inflateTransition(android.R.transition.slide_right)
        activity?.title = "Nome"
        activity?.fab?.typeNavigation = CustomFab.TypeNavigation.EnterNameType
        activity?.fab?.setImageResource(R.drawable.ic_navigate_next_white_24dp)
        activity?.let {
            (it as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }

        val binding = EnterNameFragmentBinding.bind(view)

        binding?.user = User("")
        binding?.notifyPropertyChanged(R.id.enter_name)
    }
}