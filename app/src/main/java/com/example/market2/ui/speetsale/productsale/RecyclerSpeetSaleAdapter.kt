package com.example.market2.ui.speetsale.productsale

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.market2.R

class RecyclerSpeetSaleAdapter  : RecyclerView.Adapter<RecyclerSpeetSaleAdapter.ViewHolder>(){
    class ViewHolder (view: View) : RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerSpeetSaleAdapter.ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.speet_sale_recycler,parent,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerSpeetSaleAdapter.ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 10
    }

}