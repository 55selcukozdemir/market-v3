package com.egrikiraz.market.ui.sales

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.egrikiraz.market.R

class SalesRecyclerAdapter : RecyclerView.Adapter<SalesRecyclerAdapter.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sales_recycler,parent, false)
        val cardView = view.findViewById<CardView>(R.id.sales_card_view)
        cardView.setOnClickListener(View.OnClickListener {
            val i = Intent(view.context, SaleActivity::class.java)
            view.context.startActivity(i)

        })
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 10
    }
}