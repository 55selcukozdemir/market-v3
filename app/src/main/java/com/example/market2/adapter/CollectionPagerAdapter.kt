package com.example.market2.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class CollectionPagerAdapter (fm: FragmentManager)  : FragmentStatePagerAdapter(fm){

    var fragmentList = mutableListOf<Fragment>()
    var fragmentText = mutableListOf<String>()
    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList.get(position)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentText.get(position)
    }

    public fun addFrag(fr: Fragment, str: String){
        fragmentList.add(fr)
        fragmentText.add(str)
    }

}