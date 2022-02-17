package com.example.market2.ui.speetsale.bottom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.market2.R

class RecyclerBottomAdapter(private val dataSet: Array<String>) :
    RecyclerView.Adapter<RecyclerBottomAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val textview : TextView
        init {
            textview = view.findViewById(R.id.textView2)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sale_bottom_sheet_recycler, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textview.text = dataSet[position]
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}