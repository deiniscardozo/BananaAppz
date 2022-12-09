package com.overcom.bananaapp.ui.view.fragmentes.products

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.overcom.bananaapp.R
import com.overcom.bananaapp.core.Util
import com.overcom.bananaapp.databinding.FragmentProductsBinding
import com.overcom.bananaapp.domain.model.Products
import com.overcom.bananaapp.ui.view.fragmentes.products.adapter.ProductsAdapter
import com.overcom.bananaapp.ui.view.fragmentes.thirds_detail.thirdsdetail.ThirdsDetailFragment
import com.overcom.bananaapp.ui.viewmodel.HomeViewModel
import com.overcom.bananaapp.ui.viewmodel.ProductsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProductsFragment : Fragment() {

    private var _binding: FragmentProductsBinding? = null
    private val binding get() = _binding!!
    var listProducts: MutableList<Products> = mutableListOf()
    private var mAdapter: ProductsAdapter? = null
    private val viewModelProducts: ProductsViewModel by viewModels()
    private val viewModelHome: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModelProducts.load.observe(viewLifecycleOwner) { show ->
            binding.reciclerProductsLoading.isVisible = show
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val linearLayoutManager = LinearLayoutManager(activity)
        val viewT = layoutInflater.inflate(R.layout.toast_error, null)
        val text: TextView = viewT.findViewById(R.id.toastMessage_error)
        viewModelProducts.onCreate().toString()

        viewModelHome._activateFilter.value = true

       /* binding.swipeRefreshLayout.setOnRefreshListener {
            //viewModel._position.value = 0
           // viewModel.thirdsCall(typeThirds, requireContext(), text, viewT)
            binding.swipeRefreshLayout.isRefreshing = false
        }*/

        mAdapter = ProductsAdapter(listProducts) {
            //Util.intentActivity(context, ThirdsDetailActivity::class.java)
            Util.intentFragment(
                ThirdsDetailFragment(), childFragmentManager,
                R.id.fragment_home
            )
            // BananaApp.preferences.saveThirdsID(it.id)
        }

        binding.reciclerProductsLoading.isVisible = false

        binding.reciclerProducts.apply {
            isVisible = true
            layoutManager = linearLayoutManager
            adapter = mAdapter
        }

        viewModelProducts.listProducts.observe(viewLifecycleOwner) {
            if (it != null) {
                mAdapter?.setItems(list = it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModelHome._activateFilter.value = false
        _binding = null
    }
}