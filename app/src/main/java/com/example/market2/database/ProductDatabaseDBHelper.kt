package com.example.market2.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.example.market2.database.model.ProductEntry
import com.example.market2.database.model.ProductM

class ProductDatabaseDBHelper(val contexts: Context?) : SQLiteOpenHelper(contexts, DATABASE_NAME ,null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "database.db"
    }

    private val SQL_CREATE_ENTRIES =
        "CREATE TABLE ${ProductEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${ProductEntry.PRODUCT_NAME} TEXT," +
                "${ProductEntry.BARCODE} TEXT," +
                "${ProductEntry.SALE_PRICE} REAL," +
                "${ProductEntry.PURCHASE_PRICE} REAL," +
                "${ProductEntry.NUMBER_OF_PRODUCTS} INTEGER," +
                "${ProductEntry.NUMBER_OF_PRODUCTS_ADDED} INTEGER," +
                "${ProductEntry.CATEGORY} TEXT," +
                "${ProductEntry.UNIT} TEXT," +
                "${ProductEntry.IMAGE} TEXT)"

    private val SQL_DELETE_ENTRIES =  "DROP TABLE IF EXISTS ${ProductEntry.TABLE_NAME}"


    override fun onCreate(db: SQLiteDatabase?) {
        if (db != null) {
            db.execSQL(SQL_CREATE_ENTRIES)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        if (db != null) {
            db.execSQL(SQL_DELETE_ENTRIES)
        }
        onCreate(db)
    }


    fun putProduct(mProdut: ProductM){

        val db = this.writableDatabase

        val prodoctVales = ContentValues().apply {
            put(ProductEntry.PRODUCT_NAME, mProdut.name)
            put(ProductEntry.BARCODE, mProdut.barcode)
            put(ProductEntry.SALE_PRICE, mProdut.sale_price)
            put(ProductEntry.NUMBER_OF_PRODUCTS_ADDED, mProdut.number_of_products_added)
            put(ProductEntry.CATEGORY , mProdut.category)
            put(ProductEntry.UNIT , mProdut.unit)
            put(ProductEntry.IMAGE , mProdut.image)
        }

        val newRowId = db.insert(ProductEntry.TABLE_NAME, null, prodoctVales)


    }
}