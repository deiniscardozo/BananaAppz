package com.overcom.bananaapp.domain.model
import com.overcom.bananaapp.data.database.entities.ProductsEntity
import com.overcom.bananaapp.data.model.ProductsModel

data class Products(
    val product_detail_id: Int?,
    val name: String?,
    val reference: String?,
    val image: String?,
    val ean13: String?,
    val netprice: String?,
    val sku: String?,
    val stock: String?
)

fun ProductsModel.toDomain() = Products(
    product_detail_id,
    name,
    reference,
    image,
    ean13,
    netprice,
    sku,
    stock
)

fun ProductsEntity.toDomain() = Products(
    product_detail_id,
    name,
    reference,
    image,
    ean13,
    netprice,
    sku,
    stock
)