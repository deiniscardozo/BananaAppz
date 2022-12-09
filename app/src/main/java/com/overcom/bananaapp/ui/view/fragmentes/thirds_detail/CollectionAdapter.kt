package com.overcom.bananaapp.ui.view.fragmentes.thirds_detail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.overcom.bananaapp.ui.view.fragmentes.thirds_detail.archivos.ArchivosFragment
import com.overcom.bananaapp.ui.view.fragmentes.thirds_detail.contact.ContactFragment
import com.overcom.bananaapp.ui.view.fragmentes.thirds_detail.documents.DocumentsFragment
import com.overcom.bananaapp.ui.view.fragmentes.thirds_detail.thirdsdetail.ThirdsDetailFragment

class CollectionAdapter(fm: FragmentActivity) :
    FragmentStateAdapter(fm) {

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ThirdsDetailFragment()
            1 -> ContactFragment()
            2 -> DocumentsFragment()
            3 -> ArchivosFragment()
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }
}