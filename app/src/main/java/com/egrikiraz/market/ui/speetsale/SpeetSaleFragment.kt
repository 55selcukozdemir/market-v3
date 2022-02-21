package com.egrikiraz.market.ui.speetsale

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.egrikiraz.market.R
import com.egrikiraz.market.ScannerActivity
import com.egrikiraz.market.adapter.CollectionPagerAdapter
import com.egrikiraz.market.database.model.ProductM
import com.egrikiraz.market.databinding.FragmentSpeetSaleBinding
import com.egrikiraz.market.ui.speetsale.bottom.SaleBottomSheet
import com.egrikiraz.market.ui.speetsale.productsale.SaleGridFragment
import java.util.*
import kotlin.collections.ArrayList

class SpeetSaleFragment : Fragment() {


    private val TAG = "SpeetSaleFragment"

    private lateinit var mFragment: CollectionPagerAdapter
    private lateinit var viewPager: ViewPager
    private lateinit var homeViewModel:SpeetSaleViewModel



    private var _binding: FragmentSpeetSaleBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this).get(SpeetSaleViewModel::class.java)

        _binding = FragmentSpeetSaleBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setHasOptionsMenu(true)


        viewPager = binding.viewpager
        val tapBar = binding.tabLayout
        tapBar.setupWithViewPager(viewPager)

        mFragment = CollectionPagerAdapter(childFragmentManager)


        binding.basket.setOnClickListener(View.OnClickListener {
            val saleBottomSheet = SaleBottomSheet(this)
            saleBottomSheet.show(parentFragmentManager, "blabla")
        })

        binding.speedAScan.setOnClickListener(View.OnClickListener {
            val i = Intent(context, ScannerActivity::class.java)
            startActivity(i)
        })

        binding.speedABarcode.addTextChangedListener (object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                getBarcodFilter(p0.toString())
            }

        })

        buttonClickAlgorthm()
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.speed_menu, menu)
        val menuItem = menu.findItem(R.id.speed_search)
        val searchView: SearchView = menuItem.actionView as SearchView
        searchView.queryHint = "Arama"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                getFilter(p0)
                return true
            }

        })

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        adapterRefresh()

        if (buttonClick.barcode != ""){
            binding.speedABarcode.setText(buttonClick.barcode)
            buttonClick.barcode = ""
        }
        super.onStart()
    }

    fun adapterRefresh(){
        mFragment.deleteFrag()
        val category = resources.getStringArray(R.array.array_catecory)
        for (ct in category){
            val list = ArrayList<ProductM>()
            for (product in homeViewModel.getProduct()){
                if (product.category == ct){
                    list.add(product)
                }
            }
            if (!list.isEmpty()){
                mFragment.addFrag(SaleGridFragment(list, this), ct)
            }
        }

        viewPager.adapter = mFragment

    }

    fun getFilter(contain : String?){


        mFragment.deleteFrag()
        val productFilterList = ArrayList<ProductM>()
        productFilterList.clear()
        if (contain == null || contain.isEmpty()){
            productFilterList.addAll(homeViewModel.getProduct())
        } else {
            val query = contain.trim().toLowerCase()
            homeViewModel.getProduct().forEach { productM ->
                if (productM.name.toLowerCase(Locale.ROOT).contains(query)){
                    productFilterList.add(productM)
                }
            }
        }
        val category = resources.getStringArray(R.array.array_catecory)
        for (ct in category){
            val list = ArrayList<ProductM>()
            for (product in productFilterList){
                if (product.category == ct){
                    list.add(product)
                }
            }
            if (!list.isEmpty()){
                mFragment.addFrag(SaleGridFragment(list, this), ct)
            }
        }

        viewPager.adapter = mFragment

    }

    fun getBarcodFilter(contain : String?){


        mFragment.deleteFrag()
        val productFilterList = ArrayList<ProductM>()
        productFilterList.clear()
        if (contain == null || contain.isEmpty()){
            productFilterList.addAll(homeViewModel.getProduct())
        } else {
            val query = contain.trim().toLowerCase()
            homeViewModel.getProduct().forEach { productM ->
                if (productM.barcode.toLowerCase(Locale.ROOT).contains(query)){
                    productFilterList.add(productM)
                }
            }
        }
        val category = resources.getStringArray(R.array.array_catecory)
        for (ct in category){
            val list = ArrayList<ProductM>()
            for (product in productFilterList){
                if (product.category == ct){
                    list.add(product)
                }
            }
            if (!list.isEmpty()){
                mFragment.addFrag(SaleGridFragment(list, this), ct)
            }
        }

        viewPager.adapter = mFragment

    }

    fun saleTextChange(text : String){
        binding.speedSummary.text = text
    }
    fun saleNameChange(text: String){
        binding.speedName.text = text
    }
    fun saleBarcodeTextClear(){
        binding.speedABarcode.setText("")
    }

    @SuppressLint("ResourceAsColor")
    fun buttonClickAlgorthm (){



        binding.zero.setOnClickListener {
            buttonClick.btnStatus = 0

            binding.zero.setBackgroundResource(R.drawable.on_click)
            binding.one.setBackgroundResource(R.drawable.off_click)
            binding.two.setBackgroundResource(R.drawable.off_click)
            binding.three.setBackgroundResource(R.drawable.off_click)
            binding.four.setBackgroundResource(R.drawable.off_click)
            binding.five.setBackgroundResource(R.drawable.off_click)
            binding.six.setBackgroundResource(R.drawable.off_click)
            binding.seven.setBackgroundResource(R.drawable.off_click)
            binding.eight.setBackgroundResource(R.drawable.off_click)
            binding.nine.setBackgroundResource(R.drawable.off_click)

        }

        binding.one.setOnClickListener {
            buttonClick.btnStatus = 1

            binding.zero.setBackgroundResource(R.drawable.off_click)
            binding.one.setBackgroundResource(R.drawable.on_click)
            binding.two.setBackgroundResource(R.drawable.off_click)
            binding.three.setBackgroundResource(R.drawable.off_click)
            binding.four.setBackgroundResource(R.drawable.off_click)
            binding.five.setBackgroundResource(R.drawable.off_click)
            binding.six.setBackgroundResource(R.drawable.off_click)
            binding.seven.setBackgroundResource(R.drawable.off_click)
            binding.eight.setBackgroundResource(R.drawable.off_click)
            binding.nine.setBackgroundResource(R.drawable.off_click)

        }

        binding.two.setOnClickListener {
            buttonClick.btnStatus = 2

            binding.zero.setBackgroundResource(R.drawable.off_click)
            binding.one.setBackgroundResource(R.drawable.off_click)
            binding.two.setBackgroundResource(R.drawable.on_click)
            binding.three.setBackgroundResource(R.drawable.off_click)
            binding.four.setBackgroundResource(R.drawable.off_click)
            binding.five.setBackgroundResource(R.drawable.off_click)
            binding.six.setBackgroundResource(R.drawable.off_click)
            binding.seven.setBackgroundResource(R.drawable.off_click)
            binding.eight.setBackgroundResource(R.drawable.off_click)
            binding.nine.setBackgroundResource(R.drawable.off_click)

        }

        binding.three.setOnClickListener {
            buttonClick.btnStatus = 3

            binding.zero.setBackgroundResource(R.drawable.off_click)
            binding.one.setBackgroundResource(R.drawable.off_click)
            binding.two.setBackgroundResource(R.drawable.off_click)
            binding.three.setBackgroundResource(R.drawable.on_click)
            binding.four.setBackgroundResource(R.drawable.off_click)
            binding.five.setBackgroundResource(R.drawable.off_click)
            binding.six.setBackgroundResource(R.drawable.off_click)
            binding.seven.setBackgroundResource(R.drawable.off_click)
            binding.eight.setBackgroundResource(R.drawable.off_click)
            binding.nine.setBackgroundResource(R.drawable.off_click)

        }

        binding.four.setOnClickListener {
            buttonClick.btnStatus = 4

            binding.zero.setBackgroundResource(R.drawable.off_click)
            binding.one.setBackgroundResource(R.drawable.off_click)
            binding.two.setBackgroundResource(R.drawable.off_click)
            binding.three.setBackgroundResource(R.drawable.off_click)
            binding.four.setBackgroundResource(R.drawable.on_click)
            binding.five.setBackgroundResource(R.drawable.off_click)
            binding.six.setBackgroundResource(R.drawable.off_click)
            binding.seven.setBackgroundResource(R.drawable.off_click)
            binding.eight.setBackgroundResource(R.drawable.off_click)
            binding.nine.setBackgroundResource(R.drawable.off_click)

        }
        binding.five.setOnClickListener {
            buttonClick.btnStatus = 5

            binding.zero.setBackgroundResource(R.drawable.off_click)
            binding.one.setBackgroundResource(R.drawable.off_click)
            binding.two.setBackgroundResource(R.drawable.off_click)
            binding.three.setBackgroundResource(R.drawable.off_click)
            binding.four.setBackgroundResource(R.drawable.off_click)
            binding.five.setBackgroundResource(R.drawable.on_click)
            binding.six.setBackgroundResource(R.drawable.off_click)
            binding.seven.setBackgroundResource(R.drawable.off_click)
            binding.eight.setBackgroundResource(R.drawable.off_click)
            binding.nine.setBackgroundResource(R.drawable.off_click)

        }
        binding.six.setOnClickListener {
            buttonClick.btnStatus = 6

            binding.zero.setBackgroundResource(R.drawable.off_click)
            binding.one.setBackgroundResource(R.drawable.off_click)
            binding.two.setBackgroundResource(R.drawable.off_click)
            binding.three.setBackgroundResource(R.drawable.off_click)
            binding.four.setBackgroundResource(R.drawable.off_click)
            binding.five.setBackgroundResource(R.drawable.off_click)
            binding.six.setBackgroundResource(R.drawable.on_click)
            binding.seven.setBackgroundResource(R.drawable.off_click)
            binding.eight.setBackgroundResource(R.drawable.off_click)
            binding.nine.setBackgroundResource(R.drawable.off_click)

        }
        binding.seven.setOnClickListener {
            buttonClick.btnStatus = 7

            binding.zero.setBackgroundResource(R.drawable.off_click)
            binding.one.setBackgroundResource(R.drawable.off_click)
            binding.two.setBackgroundResource(R.drawable.off_click)
            binding.three.setBackgroundResource(R.drawable.off_click)
            binding.four.setBackgroundResource(R.drawable.off_click)
            binding.five.setBackgroundResource(R.drawable.off_click)
            binding.six.setBackgroundResource(R.drawable.off_click)
            binding.seven.setBackgroundResource(R.drawable.on_click)
            binding.eight.setBackgroundResource(R.drawable.off_click)
            binding.nine.setBackgroundResource(R.drawable.off_click)

        }
        binding.eight.setOnClickListener {
            buttonClick.btnStatus = 8

            binding.zero.setBackgroundResource(R.drawable.off_click)
            binding.one.setBackgroundResource(R.drawable.off_click)
            binding.two.setBackgroundResource(R.drawable.off_click)
            binding.three.setBackgroundResource(R.drawable.off_click)
            binding.four.setBackgroundResource(R.drawable.off_click)
            binding.five.setBackgroundResource(R.drawable.off_click)
            binding.six.setBackgroundResource(R.drawable.off_click)
            binding.seven.setBackgroundResource(R.drawable.off_click)
            binding.eight.setBackgroundResource(R.drawable.on_click)
            binding.nine.setBackgroundResource(R.drawable.off_click)

        }
        binding.nine.setOnClickListener {
            buttonClick.btnStatus = 9

            binding.zero.setBackgroundResource(R.drawable.off_click)
            binding.one.setBackgroundResource(R.drawable.off_click)
            binding.two.setBackgroundResource(R.drawable.off_click)
            binding.three.setBackgroundResource(R.drawable.off_click)
            binding.four.setBackgroundResource(R.drawable.off_click)
            binding.five.setBackgroundResource(R.drawable.off_click)
            binding.six.setBackgroundResource(R.drawable.off_click)
            binding.seven.setBackgroundResource(R.drawable.off_click)
            binding.eight.setBackgroundResource(R.drawable.off_click)
            binding.nine.setBackgroundResource(R.drawable.on_click)

        }


    }

    fun buttonDefauld(){
        buttonClick.btnStatus = 1

        binding.zero.setBackgroundResource(R.drawable.off_click)
        binding.one.setBackgroundResource(R.drawable.on_click)
        binding.two.setBackgroundResource(R.drawable.off_click)
        binding.three.setBackgroundResource(R.drawable.off_click)
        binding.four.setBackgroundResource(R.drawable.off_click)
        binding.five.setBackgroundResource(R.drawable.off_click)
        binding.six.setBackgroundResource(R.drawable.off_click)
        binding.seven.setBackgroundResource(R.drawable.off_click)
        binding.eight.setBackgroundResource(R.drawable.off_click)
        binding.nine.setBackgroundResource(R.drawable.off_click)


    }
}