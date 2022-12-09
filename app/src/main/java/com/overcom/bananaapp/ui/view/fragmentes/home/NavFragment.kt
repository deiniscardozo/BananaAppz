package com.overcom.bananaapp.ui.view.fragmentes.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.overcom.bananaapp.R
import com.overcom.bananaapp.core.Util
import com.overcom.bananaapp.databinding.FragmentNavBinding
import com.overcom.bananaapp.ui.viewmodel.HomeViewModel
import com.overcom.bananaapp.ui.view.fragmentes.products.ProductsFragment
import com.overcom.bananaapp.ui.view.fragmentes.thirds.ThirdsFragment

class NavFragment : Fragment() {

    private var _binding: FragmentNavBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: HomeViewModel

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
        _binding = FragmentNavBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.iveThirds.setOnClickListener {
            viewModel._activateFilter.value = true
            Util.intentFragment(
                ThirdsFragment(), parentFragmentManager,
                R.id.nav_host_fragment_content_home
            )
        }

        binding.terceros.setOnClickListener {
            viewModel._activateFilter.value = true
            Util.intentFragment(
                ThirdsFragment(), parentFragmentManager,
                R.id.nav_host_fragment_content_home
            )
        }

        binding.ivProduct.setOnClickListener {
            viewModel._activateFilter.value = true
            Util.intentFragment(
                ProductsFragment(), parentFragmentManager,
                R.id.nav_host_fragment_content_home
            )
        }

        binding.iveProducts.setOnClickListener {
            viewModel._activateFilter.value = true
            Util.intentFragment(
                ProductsFragment(), parentFragmentManager,
                R.id.nav_host_fragment_content_home
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}