package com.overcom.bananaapp.ui.view.fragmentes.thirds_detail.archivos.adapter

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.overcom.bananaapp.data.model.Archivo
import com.overcom.bananaapp.databinding.ItemArchivosBinding
import com.overcom.bananaapp.ui.view.fragmentes.thirds_detail.archivos.WebViewActivity

class ArchivosViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemArchivosBinding.bind(view)

    fun render(archivosModel: Archivo) {

        binding.tvDocumentsName.text = archivosModel.nombre
        binding.tvDocumentsArchivo.text = archivosModel.archivo

        binding.tvDocumentsArchivo.setOnClickListener {
            val intent = Intent(binding.root.context, WebViewActivity::class.java)
            intent.putExtra("url", archivosModel.url)
            binding.root.context.startActivity(intent)
            }
    }
}