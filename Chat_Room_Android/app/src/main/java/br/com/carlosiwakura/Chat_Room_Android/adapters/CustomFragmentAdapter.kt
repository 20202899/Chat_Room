package br.com.carlosiwakura.Chat_Room_Android.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter

class CustomFragmentAdapter (framentManger: FragmentManager): FragmentPagerAdapter(framentManger) {

    var fragments = arrayListOf<Fragment>()

    override fun getItem(position: Int) = fragments[position]

    override fun getCount() = fragments.size

}