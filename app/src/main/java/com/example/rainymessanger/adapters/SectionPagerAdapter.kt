package com.example.rainymessanger.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.rainymessanger.fragments.ChatsFragment
import com.example.rainymessanger.fragments.UsersFragment

class SectionPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> return UsersFragment()
            1 -> return ChatsFragment()
        }
        return null!!
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position){
            0 -> return "Friends"
            1 -> return "chats"
        }
        return null!!
    }
}