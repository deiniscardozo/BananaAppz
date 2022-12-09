package com.overcom.bananaapp.ui.view.fragmentes.thirds_detail.thirdsdetail

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.overcom.bananaapp.R
import com.overcom.bananaapp.core.Util
import com.overcom.bananaapp.databinding.FragmentThirdsDetailBinding
import com.overcom.bananaapp.ui.view.activities.MapsActivity
import com.overcom.bananaapp.ui.viewmodel.ThirdsDetailViewModel
class ThirdsDetailFragment : Fragment() {


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_thirds_detail, container, false)
        val viewT = layoutInflater.inflate(R.layout.toast_error, null)
        val text: TextView = viewT.findViewById(R.id.toastMessage_error)
        val binding = FragmentThirdsDetailBinding.bind(view)
        val viewModel =
            activity?.let {
                ViewModelProvider(it)[ThirdsDetailViewModel::class.java]
            }!!

        viewModel.callThirdsDetail(requireContext(), text, viewT)

        viewModel.thirdsDetail.observe(viewLifecycleOwner) { detail ->

            Glide.with(binding.ivLogo.context).load(detail?.third?.logo)
                .placeholder(R.drawable.ic_thirds).into(binding.ivLogo)
            binding.subtitleTextView.text = detail?.third?.name
            binding.cifTextView.text = detail?.third?.cif
            binding.emailextView.text = detail?.third?.email
            binding.descView.setText(detail?.third?.description.toString())
            binding.creditView.setText(detail?.third?.credit_limit?.toBigDecimal()?.scale().toString())

            if (detail != null) {
                if (detail.branch_offices[0].principal == 1){
                    binding.addressView.setText(detail.branch_offices[0].address.toString())
                    binding.addressView.setAutoSizeTextTypeUniformWithConfiguration(10,100,10, 10)
                    binding.cityView.setText(detail.branch_offices[0].city)
                    binding.countryView.setText(detail.branch_offices[0].country)
                    binding.phoneView.setText(detail.branch_offices[0].phone.toString())
                    binding.phone2View.setText(detail.branch_offices[0].phone_2.toString())

                    binding.go.setOnClickListener {
                        Util.intentActivityPut(activity,
                            MapsActivity::class.java, detail.branch_offices[0].address.toString(),
                            detail.third.name.toString())
                    }
                }
            }
        }

        return view
    }

}