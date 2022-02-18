package com.example.market2.database.model

import android.provider.BaseColumns

object ProductEntry : BaseColumns {

    const val TABLE_NAME = "produckts"
    const val PRODUCT_NAME  = "name"
    const val BARCODE = "barcode"
    const val SALE_PRICE = "sale_price"
    const val PURCHASE_PRICE = "purchase_price"
    const val NUMBER_OF_PRODUCTS = "number_of_products"
    const val NUMBER_OF_PRODUCTS_ADDED = "number_of_products_added"
    const val CATEGORY = "category"
    const val UNIT = "unit"
    const val IMAGE = "image"
}

