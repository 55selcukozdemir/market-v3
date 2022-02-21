package com.egrikiraz.market.ui.stockstatus

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.egrikiraz.market.R
import com.egrikiraz.market.database.model.ProductM
import com.egrikiraz.market.databinding.FragmentStockStatusBinding

class StockStatusFragment : Fragment() {

    private val TAG = "StockStatusFragment"

    private var _binding: FragmentStockStatusBinding? = null
    private val binding get() = _binding!!

    lateinit var recyclerAdapter :StockRecyclerAdapter
    lateinit var dataList: ArrayList<ProductM>
    lateinit var slideshowViewModel: StockStatusViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        slideshowViewModel = ViewModelProvider(this).get(StockStatusViewModel::class.java)
        setHasOptionsMenu(true)
        dataListRefresh()


        _binding = FragmentStockStatusBinding.inflate(inflater, container, false)
        val root: View = binding.root

        recyclerAdapter = StockRecyclerAdapter(dataList)
        val recylerView = binding.stockRecycler
        recylerView.layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
        recylerView.adapter = recyclerAdapter


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.stock_menu, menu)
        menu.findItem(R.id.stock_add)

        val menuItem = menu.findItem(R.id.stock_search)
        val searchView: SearchView = menuItem.actionView as SearchView
        searchView.queryHint = "Arama"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                recyclerAdapter?.getFilter()?.filter(newText)
                return true
            }

        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.stock_add -> {
                val i = Intent(context, ProductAddActivity::class.java)
                i.putExtra("newAdd", "newAdd")
                startActivity(i)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun dataListRefresh(){
        dataList = slideshowViewModel.data() as ArrayList<ProductM>
    }


    override fun onStart() {
        dataListRefresh()
        recyclerAdapter = StockRecyclerAdapter(dataList)
        binding.stockRecycler.adapter = recyclerAdapter
        super.onStart()
    }




}