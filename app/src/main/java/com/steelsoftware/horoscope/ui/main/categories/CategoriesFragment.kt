package com.steelsoftware.horoscope.ui.main.categories

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.steelsoftware.horoscope.R
import com.steelsoftware.horoscope.ui.uimodels.Category
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import kotlinx.android.synthetic.main.fragment_search.*

/**
 * Created by ansh on 22/02/18.
 */
class CategoriesFragment : Fragment(), CategoriesAdapter.OnItemClickListener {

    private var admobAdview: AdView? = null

    companion object {
        fun newInstance() = CategoriesFragment()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_categories, container, false)

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val categoriesRv: androidx.recyclerview.widget.RecyclerView = view!!.findViewById(R.id.categories_recyclerView)
        admobAdview = view!!.findViewById<View>(R.id.admob_adview) as AdView

        categoriesRv.setHasFixedSize(true)
        categoriesRv.setItemViewCacheSize(10)
        categoriesRv.isDrawingCacheEnabled = true
        categoriesRv.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
        categoriesRv.layoutManager = androidx.recyclerview.widget.GridLayoutManager(activity, 3)

        val items = ArrayList<Category>()
        addItems(itemList = items)

        val adapter = CategoriesAdapter(context, items, this)
        categoriesRv.adapter = adapter

        showBannerAd()

    }

    fun addItems(itemList: ArrayList<Category>) {
        // adding categories to the list
        itemList.add(Category(getString(R.string.aries), R.drawable.ic_aries))
        itemList.add(Category(getString(R.string.taurus), R.drawable.ic_taurus))
        itemList.add(Category(getString(R.string.gemini), R.drawable.ic_gemini))
        itemList.add(Category(getString(R.string.cancer), R.drawable.ic_cancer))
        itemList.add(Category(getString(R.string.leo), R.drawable.ic_leo))
        itemList.add(Category(getString(R.string.virgo), R.drawable.ic_virgo))
        itemList.add(Category(getString(R.string.libra), R.drawable.ic_libra))
        itemList.add(Category(getString(R.string.scorpius), R.drawable.ic_scorpion))
        itemList.add(Category(getString(R.string.zodyak), R.drawable.ic_sagittarius))
        itemList.add(Category(getString(R.string.capricornus), R.drawable.ic_capricorn))
        itemList.add(Category(getString(R.string.aries), R.drawable.ic_aries))
        itemList.add(Category(getString(R.string.pisces), R.drawable.ic_pisces))
    }


    private fun showBannerAd() {
        //Using XML declaration, coz getting runtime late binding issues with following code
        //admobAdview!!.adSize = AdSize.BANNER
        //admobAdview!!.adUnitId = getString(R.string.banner_home_footer)
        val adRequest = AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                // Check the LogCat to get your test device ID
                .addTestDevice("0FD0E3B46C944EEA1F9DA3C24B9FD912")
                .build()
        admobAdview!!.adListener = object : AdListener() {
            override fun onAdLoaded() {}
            override fun onAdClosed() {
                Toast.makeText(context, "Ad is closed!", Toast.LENGTH_SHORT).show()
            }

            override fun onAdFailedToLoad(errorCode: Int) {
                Toast.makeText(context, "Ad failed to load! error code: $errorCode", Toast.LENGTH_SHORT).show()
            }

            override fun onAdLeftApplication() {
                Toast.makeText(context, "Ad left application!", Toast.LENGTH_SHORT).show()
            }

            override fun onAdOpened() {
                super.onAdOpened()
            }
        }
        admobAdview!!.loadAd(adRequest)
    }

    override fun onItemClick(category: Category) {
        val isConnected = isConnectedToInternet()
        if (isConnected) {
            val intent = Intent(activity, CategoryNewsActivity::class.java)
            intent.putExtra("CATEGORY", category.text)
            startActivity(intent)
        } else {
            Snackbar.make(this.view!!, "You are not connected to the internet", Snackbar.LENGTH_LONG).show()
        }

    }

    private fun isConnectedToInternet(): Boolean {
        val connManager = activity!!.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager

        val ni = connManager.activeNetworkInfo
        return ni != null && ni.isConnected
    }

}