package com.overcom.bananaapp.ui.view.fragmentes.thirds

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.overcom.bananaapp.BananaApp.Companion.preferences
import com.overcom.bananaapp.R
import com.overcom.bananaapp.core.Util
import com.overcom.bananaapp.data.model.ThirdsData
import com.overcom.bananaapp.databinding.FragmentThirdsBinding
import com.overcom.bananaapp.ui.view.fragmentes.thirds.adapter.ThirdsAdapter
import com.overcom.bananaapp.ui.viewmodel.HomeViewModel
import com.overcom.bananaapp.ui.view.fragmentes.thirds_detail.BlankFragment


class ThirdsFragment : Fragment() {
    private var _binding: FragmentThirdsBinding? = null
    private val binding get() = _binding!!
    var listThirds: MutableList<ThirdsData> = mutableListOf()
    private lateinit var viewModel: HomeViewModel
    private lateinit var typeThirds: String
    private var filter: String? = null
    private var positions: Int = 0
    private var mAdapter: ThirdsAdapter? = null

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
        _binding = FragmentThirdsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val linearLayoutManager = LinearLayoutManager(activity)
        val viewT = layoutInflater.inflate(R.layout.toast_error, null)
        val text: TextView = viewT.findViewById(R.id.toastMessage_error)

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel._position.value = 0
            viewModel.thirdsCall(typeThirds, requireContext(), text, viewT)
            binding.swipeRefreshLayout.isRefreshing = false
        }

        mAdapter = ThirdsAdapter(listThirds) {
            //Util.intentActivity(context, ThirdsDetailActivity::class.java)
            Util.intentFragment(
                BlankFragment(), childFragmentManager,
                R.id.nav_host_fragment_content_home)
            preferences.saveThirdsID(it.id)
        }

        binding.reciclerThirdsLoading.isVisible = false

        binding.reciclerThirds.apply {
            isVisible = true
            layoutManager = linearLayoutManager
            adapter = mAdapter

            clearOnScrollListeners()
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(
                    recyclerView: RecyclerView,
                    firstVisibleItem: Int,
                    visibleItemCount: Int
                ) { super.onScrolled(recyclerView, firstVisibleItem, visibleItemCount)
                    val totalItemCount = linearLayoutManager.itemCount
                    val lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition() + 1

                    if (totalItemCount == lastVisibleItemPosition && positions < totalItemCount
                        && filter.isNullOrEmpty()) {

                        viewModel._position.value = lastVisibleItemPosition + 1
                        viewModel.thirdsCall(typeThirds, requireContext(), text, viewT)
                   }
                }
            })
        }

        viewModel.type_third.observe(viewLifecycleOwner) {
            typeThirds = it
        }

        viewModel.filter.observe(viewLifecycleOwner) {
            filter = it
        }

        viewModel.position.observe(viewLifecycleOwner) {
            positions = it
        }

        viewModel.load.observe(viewLifecycleOwner) { show ->
            binding.reciclerThirdsLoading.isVisible = show
        }

        viewModel.listThirds.observe(viewLifecycleOwner) {
            mAdapter!!.setItems(list = it)
            Log.i("deinislist", it.toString())
        }

        viewModel.thirdsCall("customer_prospect", requireContext(), text, viewT)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel._activateFilter.value = false
        _binding = null
    }
}