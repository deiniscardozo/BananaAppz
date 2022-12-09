package com.overcom.bananaapp.data.model

data class ProductsDataItem(
    val products: List<ProductsModel>
)

data class ProductsModel(
    val beforetax: Double,
    val bulto: String,
    val category_name: Any,
    val condition_name: Any,
    val cost: Any,
    val created_at: String,
    val cuenta_ingreso_egreso_id: Any,
    val description: String,
    val ean13: String,
    val id: Int,
    val image: String,
    val is_po_tax_exempt: Any,
    val is_tax_exempt: Any,
    val manufacturer_name: String,
    val name: String,
    val netprice: String,
    val po_taxe_name: Any,
    val presentacion: String,
    val price_list_name: String,
    val product_detail_id: Int,
    val reference: String,
    val sale_price: String,
    val sku: String,
    val stock: String,
    val tax_id: Any,
    val taxe_name: Any,
    val type: Int,
    val ubicacion: String,
    val ultcosto: Int,
    val unit_name: String,
    val upc: Any,
    val updated_at: String,
    val use_lotes: Int,
    val use_series: Int,
    val warehouse_name: String
)