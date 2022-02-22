package com.egrikiraz.market.ui.sales

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.egrikiraz.market.R
import com.egrikiraz.market.database.SalesDatabaseAdapter
import com.egrikiraz.market.database.model.SoldM
import com.egrikiraz.market.database.model.SoldNameM
import com.egrikiraz.market.databinding.FragmentSalesBinding

class SalesFragment : Fragment() {

    private var _binding: FragmentSalesBinding? = null

    lateinit var soldDb : SalesDatabaseAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(SalesViewModel::class.java)

        _binding = FragmentSalesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setHasOptionsMenu(true)

        val recycleradapter = binding.salesRecycler
        recycleradapter.layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
        soldDb = SalesDatabaseAdapter(this.context)

        recycleradapter.adapter = SalesRecyclerAdapter(soldDb.getSalesName())


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.sales_menu, menu)


        val menuItem = menu.findItem(R.id.sales_search)
        val searchView: SearchView = menuItem.actionView as SearchView
        searchView.queryHint = "Arama"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }

        })


        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroy() {
        super.onDestroy()
        soldDb.close()
    }
}