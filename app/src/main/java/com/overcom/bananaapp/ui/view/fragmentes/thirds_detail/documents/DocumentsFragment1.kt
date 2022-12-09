package com.overcom.bananaapp.ui.view.fragmentes.thirds_detail.documents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.overcom.bananaapp.R
import com.overcom.bananaapp.databinding.FragmentDocumentsBinding


class DocumentsFragment1 : Fragment() {
  //  val list = ThirdsDetailFragment.listDocuments

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_documents, container, false)
        val binding = FragmentDocumentsBinding.bind(view)

        binding.reciclerDocuments.isVisible = true
        binding.reciclerDocuments.layoutManager = LinearLayoutManager(this.context)
       // binding.reciclerDocuments.adapter = DocumentsAdapter(list)

        return view
    }

    private fun openFolder(){
     //   val url: String = list[0].url
       /* if (!url.isEmpty()) {
            val intentWeb = Intent()
            intentWeb.action = Intent.ACTION_VIEW
            intentWeb.data = Uri.parse("http://$url")
            startActivity(intentWeb)
        }*/
    }

}
