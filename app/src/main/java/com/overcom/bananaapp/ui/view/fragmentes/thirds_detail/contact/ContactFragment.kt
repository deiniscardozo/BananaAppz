package com.overcom.bananaapp.ui.view.fragmentes.thirds_detail.contact


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.overcom.bananaapp.R
import com.overcom.bananaapp.databinding.FragmentContactBinding
import com.overcom.bananaapp.ui.view.fragmentes.thirds_detail.contact.adapter.ContactAdapter
import com.overcom.bananaapp.ui.viewmodel.ThirdsDetailViewModel

class ContactFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_contact, container, false)
        val binding = FragmentContactBinding.bind(view)
        val viewModel =
            activity?.let {
                ViewModelProvider(it)[ThirdsDetailViewModel::class.java]
            }!!

        viewModel.listContact.observe(viewLifecycleOwner){ list ->

            binding.reciclerContact.isVisible = true
            binding.reciclerContact.layoutManager = LinearLayoutManager(this.context)
            binding.reciclerContact.adapter = ContactAdapter(list)

        }

        return view
    }

}