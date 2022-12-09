package com.overcom.bananaapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.overcom.bananaapp.domain.model.Products

@Entity(
    tableName = "products_table",
    indices = [Index(value = ["reference"], unique = true)]
)
data class ProductsEntity
    (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idApp") val idApp: Int = 0,
    @ColumnInfo(name = "product_detail_id") val product_detail_id: Int?,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "reference") val reference: String?,
    @ColumnInfo(name = "image") val image: String?,
    @ColumnInfo(name = "ean13") val ean13: String?,
    @ColumnInfo(name = "netprice") val netprice: String?,
    @ColumnInfo(name = "sku") val sku: String?,
    @ColumnInfo(name = "stock") val stock: String?
)

//@Ignore
fun Products.toDatabase() =
    ProductsEntity(
        product_detail_id = product_detail_id,
        name = name,
        reference = reference,
        image = image,
        ean13 = ean13,
        netprice = netprice,
        sku = sku,
        stock = stock
    )
