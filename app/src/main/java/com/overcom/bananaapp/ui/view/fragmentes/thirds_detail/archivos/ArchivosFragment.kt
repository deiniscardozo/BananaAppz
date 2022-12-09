package com.overcom.bananaapp.ui.view.fragmentes.thirds_detail.archivos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.overcom.bananaapp.R
import com.overcom.bananaapp.databinding.FragmentArchivosBinding
import com.overcom.bananaapp.ui.view.fragmentes.thirds_detail.archivos.adapter.ArchivosAdapter
import com.overcom.bananaapp.ui.viewmodel.ThirdsDetailViewModel

class ArchivosFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_archivos, container, false)
        val binding = FragmentArchivosBinding.bind(view)
        val viewModel =
            activity?.let {
                ViewModelProvider(it)[ThirdsDetailViewModel::class.java]
            }!!

        viewModel.listArchivos.observe(viewLifecycleOwner) { list ->

        binding.reciclerDocuments.isVisible = true
        binding.reciclerDocuments.layoutManager = LinearLayoutManager(this.context)
        binding.reciclerDocuments.adapter = ArchivosAdapter(list)

        }

        return view
    }
}