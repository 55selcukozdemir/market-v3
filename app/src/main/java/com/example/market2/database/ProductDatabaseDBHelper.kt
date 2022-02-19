package com.example.market2.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.example.market2.database.model.ProductEntry
import com.example.market2.database.model.ProductM

class ProductDatabaseDBHelper(val contexts: Context?) : SQLiteOpenHelper(contexts, DATABASE_NAME ,null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_VERSION = 2
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
                "${ProductEntry.CATEGORY} TEXT," +
                "${ProductEntry.UNIT} TEXT," +
                "${ProductEntry.IMAGE} BLOB)"

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
            put(ProductEntry.PURCHASE_PRICE, mProdut.purchase_price)
            put(ProductEntry.NUMBER_OF_PRODUCTS, mProdut.number_of_products)
            put(ProductEntry.CATEGORY , mProdut.category)
            put(ProductEntry.UNIT , mProdut.unit)
            put(ProductEntry.IMAGE , mProdut.image)
        }

        val newRowId = db.insert(ProductEntry.TABLE_NAME, null, prodoctVales)
    }


    fun readProduct(barcode: String): MutableList<ProductM> {

        val productList = mutableListOf<ProductM>()

        val db = this.readableDatabase

        val selection = "${ProductEntry.BARCODE} = ?"
        val selectionArgs = arrayOf(barcode)
        val sortOrder = "${ProductEntry.TABLE_NAME} DESC"

        val cursor = db.query(
            ProductEntry.TABLE_NAME,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null
            )

        while (cursor.moveToNext()){
            productList.add(ProductM(
                cursor.getString(1),
                cursor.getString(2),
                cursor.getDouble(3),
                cursor.getDouble(4),
                cursor.getInt(5),
                cursor.getString(6),
                cursor.getString(7),
                cursor.getBlob(8)
                ))

//            val name: String,
//            val barcode: String,
//            val sale_price: Double,
//            val purchase_price: Double,
//            val number_of_products: Int,
//            val category: String,
//            val unit: String,
//            val image: String
        }
        return productList
    }




}