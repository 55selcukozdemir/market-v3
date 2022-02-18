package com.example.market2.database.model

data class ProductM (
    val name: String,
    val barcode: String,
    val sale_price: Float,
    val purchase_price: Float,
    val number_of_products: Int,
    val number_of_products_added: Int,
    val category: String,
    val unit: String,
    val image: String
    )