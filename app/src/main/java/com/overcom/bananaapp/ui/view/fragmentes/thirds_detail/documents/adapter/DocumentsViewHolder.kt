package com.overcom.bananaapp.ui.view.fragmentes.thirds_detail.documents.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.overcom.bananaapp.data.model.DocumentsItem
import com.overcom.bananaapp.databinding.ItemDocumentsBinding
import java.math.BigDecimal
import java.math.RoundingMode

class DocumentsViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemDocumentsBinding.bind(view)

    fun render(documentsModel: DocumentsItem) {
        val year = documentsModel.fecha_doc.subSequence(0, 4).toString()
        val month = documentsModel.fecha_doc.subSequence(6, 7).toString()
        val day = documentsModel.fecha_doc.subSequence(9, 10).toString()
        val fecdoc = "$day/$month/$year"
        val year1 = documentsModel.fecha_venc.subSequence(0, 4).toString()
        val month1 = documentsModel.fecha_venc.subSequence(6, 7).toString()
        val day1 = documentsModel.fecha_venc.subSequence(9, 10).toString()
        val fecven = "$day1/$month1/$year1"


        binding.tvDocumentsNum.text = documentsModel.menu_name + "-" +
                documentsModel.serie + "-"  +
                documentsModel.numero_doc.toString()
        binding.tvDocumentsDate.text = fecdoc
        binding.tvDocumentsExpiration.text = fecven
        binding.tvDocumentsLate.text = documentsModel.atraso.toString()
        binding.tvDocumentsNet.text =
            BigDecimal(documentsModel.neto_total).setScale(2, RoundingMode.HALF_EVEN).toString()
        binding.tvDocumentsPending.text =
            BigDecimal(documentsModel.saldo_pendiente).setScale(2, RoundingMode.HALF_EVEN).toString()
    }
}