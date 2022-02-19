package com.example.market2.ui.stockstatus

import android.app.SearchManager
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.market2.R
import com.example.market2.databinding.FragmentStockStatusBinding

class StockStatusFragment : Fragment() {

    private val TAG = "StockStatusFragment"

    private var _binding: FragmentStockStatusBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel =
                ViewModelProvider(this).get(StockStatusViewModel::class.java)

        _binding = FragmentStockStatusBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //val textView: TextView = binding.textSlideshow
        slideshowViewModel.text.observe(viewLifecycleOwner) {
            //textView.text = it
        }

        setHasOptionsMenu(true)




        val recylerView = binding.stockRecycler

        recylerView.layoutManager = GridLayoutManager(context,1, GridLayoutManager.VERTICAL, false)
        recylerView.adapter = StockRecyclerAdapter()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {


        inflater.inflate(R.menu.stock_menu,menu)

        val menuItem = menu.findItem(R.id.stock_search)
        val searchView: SearchView = menuItem.actionView as SearchView
        searchView.queryHint = "Arama"
        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                Log.d(TAG, "onQueryTextChange: $p0")

                return true
            }

        })


        super.onCreateOptionsMenu(menu, inflater)
    }


}