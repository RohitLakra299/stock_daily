package com.example.stockstoday.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.stockstoday.R
import com.example.stockstoday.models.CacheTopGainer
import com.example.stockstoday.models.CacheTopLoser
import com.example.stockstoday.models.TopChange

internal class GridViewAdapter(private var stockList: List<TopChange>, private val context : Context):BaseAdapter(){
    private var layoutInflater: LayoutInflater? = null
    private lateinit var nameTV: TextView
    private lateinit var priceTV: TextView
    private lateinit var changeTV: TextView
    override fun getCount(): Int {
        return stockList.size
    }

    override fun getItem(p0: Int): Any {
        return stockList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var view = p1
        if (layoutInflater == null) {
            layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        }
        if(view == null){
            view = layoutInflater!!.inflate(com.example.stockstoday.R.layout.item_view, null)
        }
        nameTV = view!!.findViewById(R.id.tvName)
        priceTV = view.findViewById(R.id.tvPrice)
        changeTV = view.findViewById(R.id.tvChange)

        nameTV.text = stockList[p0].ticker
        priceTV.text = stockList[p0].price
        changeTV.text = stockList[p0].changePercentage
        if(stockList[p0].changePercentage.contains("-")){
            changeTV.setTextColor(context.resources.getColor(R.color.loss,null))
        }else{
            changeTV.setTextColor(context.resources.getColor(R.color.gain,null))
        }
        return view
    }

    fun updateData(changes: List<TopChange>) {
        stockList = changes
        notifyDataSetChanged()
    }


}