package com.egrikiraz.market.database.model

data class SaleM(
    val name: String,
    val barcode: String,
    val sale_price: Double,
    val purchase_price: Double,
    val number_of_products: Int,
    val category: String,
    val unit: String,
    val image: ByteArray,
    val size: Int
)