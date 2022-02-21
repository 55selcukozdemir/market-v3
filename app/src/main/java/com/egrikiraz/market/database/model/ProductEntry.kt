package com.egrikiraz.market.database.model

import android.provider.BaseColumns

object ProductEntry : BaseColumns {

    const val TABLE_NAME = "produckts"
    const val PRODUCT_NAME  = "name"
    const val BARCODE = "barcode"
    const val SALE_PRICE = "sale_price"
    const val PURCHASE_PRICE = "purchase_price"
    const val NUMBER_OF_PRODUCTS = "number_of_products"
    const val CATEGORY = "category"
    const val UNIT = "unit"
    const val IMAGE = "image"
}

object saleEntry : BaseColumns {

    const val TABLE_NAME = "sales"
    const val PRODUCT_NAME  = "name"
    const val BARCODE = "barcode"
    const val SALE_PRICE = "sale_price"
    const val PURCHASE_PRICE = "purchase_price"
    const val NUMBER_OF_PRODUCTS = "number_of_products"
    const val CATEGORY = "category"
    const val UNIT = "unit"
    const val IMAGE = "image"
    const val SIZE = "size"

}

