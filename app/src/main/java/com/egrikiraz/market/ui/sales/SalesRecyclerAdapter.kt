package com.egrikiraz.market.ui.sales

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.egrikiraz.market.R
import com.egrikiraz.market.database.model.SoldNameM

class SalesRecyclerAdapter(val soldNameMutableList: MutableList<SoldNameM>) : RecyclerView.Adapter<SalesRecyclerAdapter.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val cardView = view.findViewById<CardView>(R.id.sales_card_view)
        val date = view.findViewById<TextView>(R.id.sales_date)
        val price = view.findViewById<TextView>(R.id.sales_price)
        val context = view.context

        fun bind(sold: SoldNameM){
            date.setText(sold.date)
            price.setText(sold.total_price + " tl")
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sales_recycler,parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(soldNameMutableList.get(position))

        holder.cardView.setOnClickListener {
            val i = Intent(holder.context, SaleActivity::class.java)
            i.putExtra("uuid", soldNameMutableList.get(position).uuid)
            holder.context.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        return soldNameMutableList.size
    }
}