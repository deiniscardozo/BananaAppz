package com.overcom.bananaapp.ui.view.fragmentes.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.overcom.bananaapp.R
import com.overcom.bananaapp.core.Util.setContentView
import com.overcom.bananaapp.databinding.ActivityMainBinding
import com.overcom.bananaapp.databinding.FragmentHomeBinding
import com.overcom.bananaapp.ui.viewmodel.HomeViewModel
import com.overcom.bananaapp.ui.viewmodel.ProductsViewModel
import javax.inject.Inject

class HomeFragment: Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =
            activity?.let {
                ViewModelProvider(it)[HomeViewModel::class.java]
            }!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewT = layoutInflater.inflate(R.layout.toast_error, null)
        val text: TextView = viewT.findViewById(R.id.toastMessage_error)
        val orgView:View = layoutInflater.inflate(R.layout.organizations, null)

        viewModel.orgSelector(parentFragmentManager, requireContext(), text, viewT, orgView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}