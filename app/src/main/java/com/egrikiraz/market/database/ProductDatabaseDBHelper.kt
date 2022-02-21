package com.egrikiraz.market.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.egrikiraz.market.database.model.ProductEntry
import com.egrikiraz.market.database.model.ProductM
import com.egrikiraz.market.database.model.SaleM
import com.egrikiraz.market.database.model.saleEntry

class ProductDatabaseDBHelper(val contexts: Context?) :
    SQLiteOpenHelper(contexts, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_VERSION = 3
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

    private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${ProductEntry.TABLE_NAME}"



    private val SQL_CREATE_SALES =
        "CREATE TABLE ${saleEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${saleEntry.PRODUCT_NAME} TEXT," +
                "${saleEntry.BARCODE} TEXT," +
                "${saleEntry.SALE_PRICE} REAL," +
                "${saleEntry.PURCHASE_PRICE} REAL," +
                "${saleEntry.NUMBER_OF_PRODUCTS} INTEGER," +
                "${saleEntry.CATEGORY} TEXT," +
                "${saleEntry.UNIT} TEXT," +
                "${saleEntry.IMAGE} BLOB," +
                "${saleEntry.SIZE} INTEGER)"

    private val SQL_DELETE_SALES = "DROP TABLE IF EXISTS ${saleEntry.TABLE_NAME}"


    override fun onCreate(db: SQLiteDatabase?) {
        if (db != null) {
            db.execSQL(SQL_CREATE_ENTRIES)
            db.execSQL(SQL_CREATE_SALES)

        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        if (db != null) {
            db.execSQL(SQL_DELETE_ENTRIES)
            db.execSQL(SQL_DELETE_SALES)
        }
        onCreate(db)
    }

    //----------------------------------------------------------------------------------------------
    fun putProduct(mProdut: ProductM) {

        val db = this.writableDatabase
        val prodoctVales = ContentValues().apply {
            put(ProductEntry.PRODUCT_NAME, mProdut.name)
            put(ProductEntry.BARCODE, mProdut.barcode)
            put(ProductEntry.SALE_PRICE, mProdut.sale_price)
            put(ProductEntry.PURCHASE_PRICE, mProdut.purchase_price)
            put(ProductEntry.NUMBER_OF_PRODUCTS, mProdut.number_of_products)
            put(ProductEntry.CATEGORY, mProdut.category)
            put(ProductEntry.UNIT, mProdut.unit)
            put(ProductEntry.IMAGE, mProdut.image)
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

        while (cursor.moveToNext()) {
            productList.add(
                ProductM(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getDouble(3),
                    cursor.getDouble(4),
                    cursor.getInt(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getBlob(8)
                )
            )

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

    fun productAllList(): MutableList<ProductM> {
        val productList = mutableListOf<ProductM>()

        val db = this.readableDatabase
        val cursor = db.query(
            ProductEntry.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )

        while (cursor.moveToNext()) {
            productList.add(
                ProductM(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getDouble(3),
                    cursor.getDouble(4),
                    cursor.getInt(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getBlob(8)
                )
            )
        }
        return productList
    }

    fun prodctDelete(barcode: String){
        val db = this.writableDatabase
        val whereClause = "${ProductEntry.BARCODE}=?"
        val whereArgs = arrayOf<String>(java.lang.String.valueOf(barcode))
        db.delete(ProductEntry.TABLE_NAME, whereClause, whereArgs)
    }


    fun productUpdate(mProdut: ProductM){

        val db = this.writableDatabase
        val whereClause = "${ProductEntry.BARCODE}=?"
        val whereArgs = arrayOf<String>(java.lang.String.valueOf(mProdut.barcode))
        val prodoctVales = ContentValues().apply {
            put(ProductEntry.PRODUCT_NAME, mProdut.name)
            put(ProductEntry.BARCODE, mProdut.barcode)
            put(ProductEntry.SALE_PRICE, mProdut.sale_price)
            put(ProductEntry.PURCHASE_PRICE, mProdut.purchase_price)
            put(ProductEntry.NUMBER_OF_PRODUCTS, mProdut.number_of_products)
            put(ProductEntry.CATEGORY, mProdut.category)
            put(ProductEntry.UNIT, mProdut.unit)
            put(ProductEntry.IMAGE, mProdut.image)
        }

        db.update(ProductEntry.TABLE_NAME,prodoctVales,whereClause,whereArgs)
    }

    //----------------------------------------------------------------------------------------------

    fun putSale(saleM: SaleM){

        val db = this.writableDatabase
        val prodoctVales = ContentValues().apply {
            put(saleEntry.PRODUCT_NAME, saleM.name)
            put(saleEntry.BARCODE, saleM.barcode)
            put(saleEntry.SALE_PRICE, saleM.sale_price)
            put(saleEntry.PURCHASE_PRICE, saleM.purchase_price)
            put(saleEntry.NUMBER_OF_PRODUCTS, saleM.number_of_products)
            put(saleEntry.CATEGORY, saleM.category)
            put(saleEntry.UNIT, saleM.unit)
            put(saleEntry.IMAGE, saleM.image)
            put(saleEntry.SIZE, saleM.size)
        }

        val newRowId = db.insert(saleEntry.TABLE_NAME, null, prodoctVales)

    }

    fun deleteSale(){
        val db = this.writableDatabase
        db.delete(saleEntry.TABLE_NAME, null, null)
    }

    fun readSale(barcode: String) : MutableList<SaleM>{

        val saleList = mutableListOf<SaleM>()

        val db = this.readableDatabase

        val selection = "${saleEntry.BARCODE} = ?"
        val selectionArgs = arrayOf(barcode)

        val cursor = db.query(
            saleEntry.TABLE_NAME,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        while (cursor.moveToNext()) {
            saleList.add(
                SaleM(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getDouble(3),
                    cursor.getDouble(4),
                    cursor.getInt(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getBlob(8),
                    cursor.getInt(9)
                )
            )

//            val name: String,
//            val barcode: String,
//            val sale_price: Double,
//            val purchase_price: Double,
//            val number_of_products: Int,
//            val category: String,
//            val unit: String,
//            val image: String
        }
        return saleList
    }

    fun saleAllList() : MutableList<SaleM>{
        val saleList = mutableListOf<SaleM>()

        val db = this.readableDatabase
        val cursor = db.query(
            saleEntry.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )

        while (cursor.moveToNext()) {
            saleList.add(
                SaleM(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getDouble(3),
                    cursor.getDouble(4),
                    cursor.getInt(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getBlob(8),
                    cursor.getInt(9)
                )
            )
        }
        return saleList
    }

    fun saleDelete(barcode: String){
        val db = this.writableDatabase
        val whereClause = "${saleEntry.BARCODE}=?"
        val whereArgs = arrayOf<String>(java.lang.String.valueOf(barcode))
        db.delete(saleEntry.TABLE_NAME, whereClause, whereArgs)
    }

    fun saleUpdate(saleM: SaleM){

        val db = this.writableDatabase
        val whereClause = "${saleEntry.BARCODE} =?"
        val whereArgs = arrayOf<String>(java.lang.String.valueOf(saleM.barcode))

        val prodoctVales = ContentValues().apply {
            put(saleEntry.PRODUCT_NAME, saleM.name)
            put(saleEntry.BARCODE, saleM.barcode)
            put(saleEntry.SALE_PRICE, saleM.sale_price)
            put(saleEntry.PURCHASE_PRICE, saleM.purchase_price)
            put(saleEntry.NUMBER_OF_PRODUCTS, saleM.number_of_products)
            put(saleEntry.CATEGORY, saleM.category)
            put(saleEntry.UNIT, saleM.unit)
            put(saleEntry.IMAGE, saleM.image)
            put(saleEntry.SIZE, saleM.size)
        }
        db.update(saleEntry.TABLE_NAME,prodoctVales,whereClause,whereArgs)
    }
}