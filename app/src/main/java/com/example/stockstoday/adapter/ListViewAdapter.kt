package com.example.stockstoday.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.stockstoday.R
import com.example.stockstoday.models.BestMatches
import com.example.stockstoday.models.SearchModel

class ListViewAdapter(
    private val context: Context,
    private var dataSource: List<BestMatches>
) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = convertView ?: inflater.inflate(R.layout.list_view_item, parent, false)

        val typeTextView = rowView.findViewById<TextView>(R.id.tv_type)
        val symbolTextView = rowView.findViewById<TextView>(R.id.tv_symbol)
        val searchResult = getItem(position) as BestMatches
        typeTextView.text = searchResult.type
        symbolTextView.text = searchResult.symbol
        return rowView
    }
    fun updateList(newList: List<BestMatches>){
        dataSource = newList
        notifyDataSetChanged()
    }
}