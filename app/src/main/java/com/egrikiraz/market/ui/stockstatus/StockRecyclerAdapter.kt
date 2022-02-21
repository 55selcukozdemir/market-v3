package com.egrikiraz.market.ui.stockstatus

import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.egrikiraz.market.R
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.egrikiraz.market.database.model.ProductM
import java.util.*
import kotlin.collections.ArrayList

class StockRecyclerAdapter(private var productDataList: ArrayList<ProductM>) :
    RecyclerView.Adapter<StockRecyclerAdapter.ViewHolder>() {


    val initialProductDataList = ArrayList<ProductM>().apply {
        productDataList?.let { addAll(it) }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var context= view.context
        var imageView = view.findViewById<ImageView>(R.id.stock_r_imageview)
        var name = view.findViewById<TextView>(R.id.stock_r_name)
        var price = view.findViewById<TextView>(R.id.stock_r_price)
        var cardView = view.findViewById<CardView>(R.id.stock_r_cardview)


        fun bind(productM: ProductM) {
            val bitmap = BitmapFactory.decodeByteArray(productM.image, 0, productM.image.size)
            imageView.setImageBitmap(bitmap)
            name.setText(productM.name)
            price.setText(productM.sale_price.toString())


        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StockRecyclerAdapter.ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.stock_recycler, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: StockRecyclerAdapter.ViewHolder, position: Int) {
        holder.bind(productDataList.get(position))
        holder.cardView.setOnClickListener(View.OnClickListener {
            val i = Intent(holder.context, ProductAddActivity::class.java)
            i.putExtra("barcode", productDataList.get(position).barcode)
            holder.context.startActivity(i)
        })
    }

    override fun getItemCount(): Int {
        return productDataList.size
    }

    fun getFilter(): Filter {
        return productFiliter
    }

    private val productFiliter = object : Filter() {
        override fun performFiltering(constrain: CharSequence?): FilterResults {
            val productFilterList: ArrayList<ProductM> = ArrayList()
            if (constrain == null || constrain.isEmpty()) {
                initialProductDataList.let { productFilterList.addAll(it) }
            } else {
                val query = constrain.toString().trim().toLowerCase()
                initialProductDataList.forEach { product ->
                    if (product.name.toLowerCase(Locale.ROOT).contains(query)) {
                        productFilterList.add(product)
                    }
                }
            }

            val result = FilterResults()
            result.values = productFilterList
            return result
        }

        override fun publishResults(constrain: CharSequence?, result: FilterResults?) {
            if (result?.values is ArrayList<*>) {
                productDataList.clear()
                productDataList.addAll(result.values as ArrayList<ProductM>)
                notifyDataSetChanged()
            }
        }
    }
}