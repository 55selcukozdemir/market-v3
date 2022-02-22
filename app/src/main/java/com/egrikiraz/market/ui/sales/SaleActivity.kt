package com.egrikiraz.market.ui.sales

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.egrikiraz.market.R
import com.egrikiraz.market.database.SalesDatabaseAdapter

class SaleActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var price: TextView
    lateinit var date: TextView

    val soldDB = SalesDatabaseAdapter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sale)

        val uuid = intent.getStringExtra("uuid")


        recyclerView = findViewById(R.id.sale_recycler)
        price = findViewById(R.id.sale_a_price)
        date = findViewById(R.id.sale_a_date)

        val saleName = soldDB.getSalesName()
        for (sale in saleName){
            price.setText("Toplam : ${sale.total_price} tl")
            date.setText(sale.date)
        }



        recyclerView.layoutManager = GridLayoutManager(applicationContext, 1, GridLayoutManager.VERTICAL, false)

        if (uuid != null) {
            val soldlist = soldDB.getUuidSales(uuid)
            recyclerView.adapter = SaleRecyclerAdapter(soldlist)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        soldDB.close()

    }
}