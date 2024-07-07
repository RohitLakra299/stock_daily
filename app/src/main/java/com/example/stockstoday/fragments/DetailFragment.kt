package com.example.stockstoday.fragments


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.stockstoday.R
import com.example.stockstoday.databinding.FragmentDetailBinding
import com.example.stockstoday.models.CacheTime
import com.example.stockstoday.utility.CustomMarker
import com.example.stockstoday.utility.DataState
import com.example.stockstoday.viewmodels.DetailFragmentViewModel
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.lang.Float.min
import kotlin.math.max


@AndroidEntryPoint
class DetailFragment : Fragment() {

    lateinit var binding : FragmentDetailBinding
    private val viewModel : DetailFragmentViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        val price = arguments?.getString("price")?: "N/A"
        val change = arguments?.getString("changePercentage")?: "0%"
        val name = arguments?.getString("name")?: "Name"
        val currency = arguments?.getString("currency")?: "Currency"

        binding.tvPrice.text = price
        binding.tvChange.text = change
        binding.tvName.text = name
        binding.tvCurrency.text = currency

        if(change!!.contains("-")){
            binding.tvChange.setTextColor(resources.getColor(R.color.loss,null))
            binding.tvch.text = "Loss : "
        }else{
            binding.tvChange.setTextColor(resources.getColor(R.color.gain,null))
            binding.tvch.text = "Gain : "
        }
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                viewModel.fetchData(arguments?.getString("ticker").toString())
            } catch (e: Exception) {
               Log.e("@@Detail", e.toString())
            }
        }

        viewModel.dataStateCompanyDetail.observe(viewLifecycleOwner){
            Log.d("@@Detail", it.toString())
            when(it){
                is DataState.Loading ->{
                    //Shimmer
                    binding.shimmerViewContainer.startShimmer()
                }
                is DataState.Success ->{
                    binding.tvName.text = it.data?.Name
                    binding.tvDesc.text = it.data?.Description
                    binding.tvCurrency.text = it.data?.Currency
                    if(binding.tvPrice.text == "N/A"){
                        binding.tvPrice.text = it.data?.AnalystTargetPrice?: "N/A"
                    }
                    if(binding.tvChange.text == "0%"){
                        binding.tvChange.text = it.data?.ProfitMargin?: "0%"
                    }
                    binding.chipSector.text = "Sector : ${it.data?.Sector?: "N/A"}"
                    binding.chipIndustry.text = "Industry : ${it.data?.Industry?: "N/A"}"
                    binding.shimmerViewContainer.showShimmer(true)
                }
                is DataState.Error ->{
                    Toast.makeText(requireContext(), "Error Occurred, please try again later", Toast.LENGTH_SHORT).show()
                    binding.shimmerViewContainer.stopShimmer()
                    binding.shimmerViewContainer.hideShimmer()
                }
                is DataState.Empty ->{
                    Toast.makeText(requireContext(), "Check your connection", Toast.LENGTH_SHORT).show()
                    binding.shimmerViewContainer.stopShimmer()
                    binding.shimmerViewContainer.hideShimmer()
                }
            }
        }
        viewModel.dataStateTimeSeries.observe(viewLifecycleOwner){
           when(it){
                is DataState.Loading ->{
                    binding.shimmerViewContainer.showShimmer(true)
                }
                is DataState.Success ->{
                    loadGraph(it.data, change.contains("-"))
                    binding.shimmerViewContainer.stopShimmer()
                    binding.shimmerViewContainer.hideShimmer()
                }
                is DataState.Error ->{

                    binding.shimmerViewContainer.stopShimmer()
                    binding.shimmerViewContainer.hideShimmer()
                }
                is DataState.Empty ->{

                    binding.shimmerViewContainer.stopShimmer()
                    binding.shimmerViewContainer.hideShimmer()
                }
           }
        }

        return binding.root
    }
    private fun loadGraph(data : List<CacheTime>?, change : Boolean){
        val lineChart = binding.lineChart
        val entries = ArrayList<Entry>()
        var timei = 0f
        var timeStamps = mutableListOf<String>()
        var maxi = 0f
        var mini = 100000f
        var timee = 0f
        if (data != null) {
            for(i in data){
                entries.add(Entry(timee, i.close!!.toFloat()))
                timeStamps.add(i.timestamp?: "N/A")
                if(timei < 11){
                    maxi = max(maxi,i.close.toFloat())
                    mini = min(mini,i.close.toFloat())
                }
                timee += 5
                timei ++
            }
        }
        Log.d("@@DetailGraph", "$maxi $mini")
        val vl = LineDataSet(entries, "Price")
        vl.setDrawFilled(true)
        if(!change){
            vl.fillColor = resources.getColor(R.color.gain,null)
            vl.color = resources.getColor(R.color.gain,null)
            vl.setCircleColor(resources.getColor(R.color.gain,null))
        }else{
            vl.fillColor = resources.getColor(R.color.loss,null)
            vl.color = resources.getColor(R.color.loss,null)
            vl.setCircleColor(resources.getColor(R.color.loss,null))
        }
        vl.lineWidth = 3f
        vl.setDrawValues(false)
        lineChart.data = LineData(vl)
        lineChart.axisRight.isEnabled = false
        lineChart.xAxis.apply {
            isGranularityEnabled = true
            granularity = 1f
            valueFormatter = IndexAxisValueFormatter(timeStamps)
            setLabelCount(6, true)
            textColor = resources.getColor(R.color.black,null)
        }
        lineChart.axisLeft.axisMaximum = maxi+0.1f
        lineChart.axisLeft.axisMinimum = mini-0.1f
        lineChart.axisLeft.textColor = resources.getColor(R.color.black,null)
        lineChart.xAxis.axisMaximum = timeStamps.size+0.1f
        val markerView = CustomMarker(requireContext().applicationContext, R.layout.marker_view)
        lineChart.marker = markerView
        lineChart.description.text = "Days"
        lineChart.description.textColor = resources.getColor(R.color.black,null)
        lineChart.description.textSize = 12f
        lineChart.legend.apply {
            textSize = 12f
            textColor = resources.getColor(R.color.black,null)
        }
        lineChart.setVisibleXRangeMaximum(min(50f,timei))
        lineChart.setTouchEnabled(true)
        lineChart.setPinchZoom(true)
        lineChart.invalidate()
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigateUp()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

}