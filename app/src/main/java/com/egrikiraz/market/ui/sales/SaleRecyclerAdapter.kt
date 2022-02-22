package com.egrikiraz.market.ui.sales

import android.graphics.BitmapFactory
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.egrikiraz.market.R
import com.egrikiraz.market.database.ProductDatabaseDBHelper
import com.egrikiraz.market.database.model.SoldM

class SaleRecyclerAdapter(val sales : MutableList<SoldM>): RecyclerView.Adapter<SaleRecyclerAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val name = view.findViewById<TextView>(R.id.sold_r_name)
        val cound = view.findViewById<TextView>(R.id.sold_r_cound)
        val price = view.findViewById<TextView>(R.id.sold_r_price)
        val image = view.findViewById<ImageView>(R.id.sold_r_image)

        val db = ProductDatabaseDBHelper(view.context)
        fun bind(sold: SoldM){
            name.setText(sold.name)
            cound.setText(sold.size.toString())
            price.setText("${sold.size} * ${sold.sale_price} = ${sold.size * sold.sale_price}")


            for (list in db.readProduct(sold.barcode)){
                val bitmap = BitmapFactory.decodeByteArray(list.image,0, list.image.size)
                image.setImageBitmap(bitmap)
            }

            db.close()
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sale_recycler,parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(sales.get(position))

    }

    override fun getItemCount(): Int {
        return sales.size
    }
}