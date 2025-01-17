package com.example.stockstoday.utility

import android.content.Context
import android.widget.TextView
import com.example.stockstoday.R
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF

class CustomMarker(context : Context, layoutResource: Int):  MarkerView(context, layoutResource)  {
    override fun refreshContent(entry: Entry?, highlight: Highlight?) {
        val value = entry?.y?.toDouble() ?: 0.0
        var resText = ""
        if(value.toString().length > 8){
            resText = "Val: " + value.toString().substring(0,7)
        }
        else{
            resText = "Val: " + value.toString()
        }
        findViewById<TextView>(R.id.tvPrice).text = resText

        super.refreshContent(entry, highlight)
    }

    override fun getOffsetForDrawingAtPoint(xpos: Float, ypos: Float): MPPointF {
        return MPPointF(-width / 2f, -height - 10f)
    }
}