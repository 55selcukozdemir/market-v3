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

object salesEntry : BaseColumns {

    const val TABLE_NAME = "sale_product"
    const val PRODUCT_NAME  = "name"
    const val BARCODE = "barcode"
    const val SALE_PRICE = "sale_price"
    const val PURCHASE_PRICE = "purchase_price"
    const val NUMBER_OF_PRODUCTS = "number_of_products"
    const val CATEGORY = "category"
    const val UNIT = "unit"
    const val IMAGE = "image"
    const val SIZE = "size"
    const val UUID = "uuid"

}

object salesNameEntry : BaseColumns {

    const val TABLE_NAME = "sale_product_master"
    const val DATE  = "date"
    const val TOTAL_PRICE = "total_price"
    const val UUID = "uuid"

}

