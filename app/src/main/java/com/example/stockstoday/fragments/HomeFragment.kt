package com.example.stockstoday.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.stockstoday.R
import com.example.stockstoday.adapter.GridViewAdapter
import com.example.stockstoday.databinding.FragmentHomeBinding
import com.example.stockstoday.models.TopChange
import com.example.stockstoday.utility.DataState
import com.example.stockstoday.viewmodels.HomeFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding
    private lateinit var gridView: GridView
    private val viewModel : HomeFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        gridView = binding.gridView
        val adapter = GridViewAdapter(emptyList(),requireContext())
        gridView.adapter = adapter

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_search, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_search -> {
                        findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner)

        gridView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val item = parent.getItemAtPosition(position) as TopChange
            val bundle = Bundle().apply {
                putString("ticker", item.ticker)
                putString("price", item.price)
                putString("changePercentage", item.changePercentage)
            }
            val navOptions = NavOptions.Builder()
                .setLaunchSingleTop(true)
                .setPopUpTo(R.id.homeFragment,false)
                .build()
            view.findNavController().navigate(R.id.action_homeFragment_to_detailFragment,bundle,navOptions)
        }
        viewModel.dataStateTopChange.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Loading -> {
                    Log.d("@@Home", "Loading")
                    binding.shimmerViewContainer.showShimmer(true)
                }
                is DataState.Success -> {
                    adapter.updateData(it.data?: emptyList())
                    binding.shimmerViewContainer.stopShimmer()
                    binding.shimmerViewContainer.hideShimmer()
                }
                is DataState.Error -> {
                    Log.e("@@Home", "Error Occurred", it.exception)
                    Toast.makeText(requireContext(), "Error Occurred, Please try again later", Toast.LENGTH_SHORT).show()
                    binding.shimmerViewContainer.stopShimmer()
                    binding.shimmerViewContainer.hideShimmer()
                }
                is DataState.Empty -> {
                    Toast.makeText(requireContext(), "Something went wrong, Please try again later", Toast.LENGTH_SHORT).show()
                    binding.shimmerViewContainer.stopShimmer()
                    binding.shimmerViewContainer.hideShimmer()
                }
            }
        }
        binding.btnGainers.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.getAllGainersAndLosers(true)
            }
        }

        binding.btnLosers.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.getAllGainersAndLosers(false)
            }
        }
        return binding.root
    }


}