package com.example.market2.database.model

data class ProductM(
    val name: String,
    val barcode: String,
    val sale_price: Double,
    val purchase_price: Double,
    val number_of_products: Int,
    val category: String,
    val unit: String,
    val image: ByteArray
    )