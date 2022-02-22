package com.egrikiraz.market.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.egrikiraz.market.database.model.*
import com.egrikiraz.market.ui.sales.SalesFragment
import java.util.*

class SalesDatabaseAdapter(val context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_VERSION = 4
        const val DATABASE_NAME = "sales.db"
    }

    private val SQL_CREATE_SALE_PRODUCT =
        "CREATE TABLE ${salesEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${salesEntry.PRODUCT_NAME} TEXT," +
                "${salesEntry.BARCODE} TEXT," +
                "${salesEntry.SALE_PRICE} REAL," +
                "${salesEntry.PURCHASE_PRICE} REAL," +
                "${salesEntry.NUMBER_OF_PRODUCTS} INTEGER," +
                "${salesEntry.CATEGORY} TEXT," +
                "${salesEntry.UNIT} TEXT," +
                "${salesEntry.IMAGE} BLOB," +
                "${salesEntry.SIZE} TEXT," +
                "${salesEntry.UUID} TEXT)"

    private val SQL_CREATE_SALE_PRODUCT_NAME =
        "CREATE TABLE ${salesNameEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${salesNameEntry.DATE} TEXT," +
                "${salesNameEntry.TOTAL_PRICE} TEXT," +
                "${salesNameEntry.UUID} TEXT)"

    private val SQL_DELETE_SALE_PRODUCT_NAME = "DROP TABLE IF EXISTS ${salesNameEntry.TABLE_NAME}"
    private val SQL_DELETE_SALE_PRODUCT = "DROP TABLE IF EXISTS ${salesEntry.TABLE_NAME}"


    override fun onCreate(p0: SQLiteDatabase?) {
        if (p0 != null) {
            p0.execSQL(SQL_CREATE_SALE_PRODUCT)
            p0.execSQL(SQL_CREATE_SALE_PRODUCT_NAME)
        }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        if (p0 != null) {
            p0.execSQL(SQL_DELETE_SALE_PRODUCT)
            p0.execSQL(SQL_DELETE_SALE_PRODUCT_NAME)
        }
        onCreate(p0)
    }


    fun getSalesName(): MutableList<SoldNameM> {

        val soldList = mutableListOf<SoldNameM>()


        val db = this.writableDatabase

        val cursor = db.query(
            salesNameEntry.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )

        while (cursor.moveToNext()) {
            soldList.add(
                SoldNameM(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)
                )
            )
        }

        return soldList
    }

    fun putProduct(soldMutableList: MutableList<SoldM>, soldNameM: SoldNameM) {

        val db = this.writableDatabase

        for (sold in soldMutableList) {
            val prodoctVales = ContentValues().apply {
                put(salesEntry.PRODUCT_NAME, sold.name)
                put(salesEntry.BARCODE, sold.barcode)
                put(salesEntry.SALE_PRICE, sold.sale_price)
                put(salesEntry.PURCHASE_PRICE, sold.purchase_price)
                put(salesEntry.NUMBER_OF_PRODUCTS, sold.number_of_products)
                put(salesEntry.CATEGORY, sold.category)
                put(salesEntry.UNIT, sold.unit)
                put(salesEntry.SIZE, sold.size)
                put(salesEntry.UUID, sold.uuid)
            }
            db.insert(salesEntry.TABLE_NAME, null, prodoctVales)
        }

        val salesName = ContentValues().apply {
            put(salesNameEntry.DATE, soldNameM.date)
            put(salesNameEntry.TOTAL_PRICE, soldNameM.total_price)
            put(salesNameEntry.UUID, soldNameM.uuid)
        }
        db.insert(salesNameEntry.TABLE_NAME, null, salesName)


    }


    fun getUuidSales(uuid: String): MutableList<SoldM> {

        val db = this.writableDatabase

        val soldMutableList = mutableListOf<SoldM>()

        val selection = "${salesEntry.UUID} = ?"
        val selectionArgs = arrayOf(uuid)

        val cursor = db.query(
            salesEntry.TABLE_NAME,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null
        )



        with(cursor){
        while (cursor.moveToNext()) {

            if (cursor.getBlob(getColumnIndexOrThrow(salesEntry.IMAGE)) != null) {

                soldMutableList.add(
                    SoldM(
                        cursor.getString(getColumnIndexOrThrow(salesEntry.PRODUCT_NAME)),
                        cursor.getString(getColumnIndexOrThrow(salesEntry.BARCODE)),
                        cursor.getDouble(getColumnIndexOrThrow(salesEntry.SALE_PRICE)),
                        cursor.getDouble(getColumnIndexOrThrow(salesEntry.PURCHASE_PRICE)),
                        cursor.getInt(getColumnIndexOrThrow(salesEntry.NUMBER_OF_PRODUCTS)),
                        cursor.getString(getColumnIndexOrThrow(salesEntry.CATEGORY)),
                        cursor.getString(getColumnIndexOrThrow(salesEntry.UNIT)),
                        cursor.getBlob(getColumnIndexOrThrow(salesEntry.IMAGE)),
                        cursor.getInt(getColumnIndexOrThrow(salesEntry.SIZE)),
                        cursor.getString(getColumnIndexOrThrow(salesEntry.UUID))
                    )
                )
            } else {
                soldMutableList.add(
                    SoldM(
                        cursor.getString(getColumnIndexOrThrow(salesEntry.PRODUCT_NAME)),
                        cursor.getString(getColumnIndexOrThrow(salesEntry.BARCODE)),
                        cursor.getDouble(getColumnIndexOrThrow(salesEntry.SALE_PRICE)),
                        cursor.getDouble(getColumnIndexOrThrow(salesEntry.PURCHASE_PRICE)),
                        cursor.getInt(getColumnIndexOrThrow(salesEntry.NUMBER_OF_PRODUCTS)),
                        cursor.getString(getColumnIndexOrThrow(salesEntry.CATEGORY)),
                        cursor.getString(getColumnIndexOrThrow(salesEntry.UNIT)),
                        ByteArray(2),
                        cursor.getInt(getColumnIndexOrThrow(salesEntry.SIZE)),
                        cursor.getString(getColumnIndexOrThrow(salesEntry.UUID))
                    )
                )
            }
        }

        }

        return soldMutableList


    }


}