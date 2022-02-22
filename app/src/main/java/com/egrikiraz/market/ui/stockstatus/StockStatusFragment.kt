package com.egrikiraz.market.ui.stockstatus

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.os.Bundle
import android.transition.Transition
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.SearchView
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.egrikiraz.market.R
import com.egrikiraz.market.database.ProductDatabaseDBHelper
import com.egrikiraz.market.database.model.ProductEntry
import com.egrikiraz.market.database.model.ProductM
import com.egrikiraz.market.databinding.FragmentStockStatusBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.*
import java.net.HttpURLConnection
import java.net.HttpURLConnection.HTTP_OK
import java.net.URL
import java.net.URLConnection

class StockStatusFragment : Fragment() {

    private val TAG = "StockStatusFragment"

    private var _binding: FragmentStockStatusBinding? = null
    private val binding get() = _binding!!

    lateinit var recyclerAdapter: StockRecyclerAdapter
    lateinit var dataList: ArrayList<ProductM>
    lateinit var slideshowViewModel: StockStatusViewModel

    lateinit var storage: FirebaseFirestore
    lateinit var firestore: FirebaseFirestore


    class someTask(var context: Context?) : AsyncTask<Void, Void, Bitmap>() {

        lateinit var firestore: FirebaseFirestore


        override fun doInBackground(vararg params: Void?): Bitmap? {
            firestore = Firebase.firestore
            var db = ProductDatabaseDBHelper(context)


            firestore.collection("product").get().addOnSuccessListener {
                if (it != null) {
                    for (i in it) {
                        if (db.readProduct(i.get(ProductEntry.BARCODE) as String)
                                .isEmpty()
                        ) {
                            try {
                                context?.let {
                                    Glide.with(it)
                                        .asBitmap()
                                        .load(i.get("url") as String)
                                        .into(object : SimpleTarget<Bitmap>() {
                                            override fun onResourceReady(
                                                resource: Bitmap,
                                                transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
                                            ) {
                                                val bos = ByteArrayOutputStream()
                                                resource.compress(
                                                    Bitmap.CompressFormat.PNG,
                                                    100,
                                                    bos
                                                )
                                                val img = bos.toByteArray()

                                                db.putProduct(
                                                    ProductM(
                                                        i.get(ProductEntry.PRODUCT_NAME) as String,
                                                        i.get(ProductEntry.BARCODE) as String,
                                                        (i.get(ProductEntry.SALE_PRICE) as String).toDouble(),
                                                        (i.get(ProductEntry.PURCHASE_PRICE) as String).toDouble(),
                                                        (i.get(ProductEntry.NUMBER_OF_PRODUCTS) as String).toInt(),
                                                        i.get(ProductEntry.CATEGORY) as String,
                                                        i.get(ProductEntry.UNIT) as String,
                                                        img
                                                    )
                                                )
                                            }

                                        })
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                            } finally {

                            }
                        }
                    }
                }
            }
            return null
        }

        override fun onPostExecute(result: Bitmap?) {

        }
    }


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
        return when (item.itemId) {
            R.id.stock_add -> {
                val i = Intent(context, ProductAddActivity::class.java)
                i.putExtra("newAdd", "newAdd")
                startActivity(i)
                true
            } R.id.stock_fire -> {
                someTask(context).execute()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun dataListRefresh() {
        dataList = slideshowViewModel.data() as ArrayList<ProductM>
    }


    override fun onStart() {
        dataListRefresh()
        recyclerAdapter = StockRecyclerAdapter(dataList)
        binding.stockRecycler.adapter = recyclerAdapter
        super.onStart()
    }


}