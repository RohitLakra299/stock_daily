package com.example.stockstoday.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.stockstoday.R
import com.example.stockstoday.adapter.ListViewAdapter
import com.example.stockstoday.databinding.FragmentSearchBinding
import com.example.stockstoday.models.BestMatches
import com.example.stockstoday.utility.DataState
import com.example.stockstoday.viewmodels.SearchFragmentViewModel
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
    lateinit var binding : FragmentSearchBinding
    private val viewModel : SearchFragmentViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        var filter = "All"
        binding.textInputEditText.setOnFocusChangeListener { _, b ->
            binding.chipGroup.visibility = if(b) View.VISIBLE else View.GONE
        }
        val adapter = ListViewAdapter(requireContext(), emptyList())
        binding.listView.adapter = adapter
        binding.textInputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.toString().isNotEmpty()){
                    viewLifecycleOwner.lifecycleScope.launch{
                        try {
                            viewModel.getSearchResults(s.toString())
                        }catch (e:Exception){
                            Log.e("@@Search",e.message.toString())
                        }
                    }

                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        binding.textInputLayout.setEndIconOnClickListener {
            binding.textInputEditText.setText("")
            adapter.updateList(emptyList())
        }
        binding.chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            val checkedChipId = group.checkedChipId
            if(checkedChipId == View.NO_ID){
                filter = "All"
                return@setOnCheckedStateChangeListener
            }
            filter = group.findViewById<Chip>(checkedChipId).text.toString()
            viewLifecycleOwner.lifecycleScope.launch{
                try {
                    if(binding.textInputEditText.text != null || binding.textInputEditText.text.toString().isNotEmpty()){
                        viewModel.getSearchResults(binding.textInputEditText.text.toString())
                        var text = binding.textInputEditText.text.toString()
                        if(text.isNotEmpty()){
                            viewModel.getSearchResults(text)
                        }
                    }
                }catch (e:Exception){
                    Log.e("@@Search",e.message.toString())
                }
            }
        }
        viewModel.dataStateSearchResult.observe(viewLifecycleOwner){
            when(it){
                is DataState.Loading -> {

                }
                is DataState.Success -> {
                    var searchList = mutableListOf<BestMatches>()
                    it.data?.bestMatches?.forEach { searchModel ->
                        if(searchModel.type == filter || filter == "All"){
                            searchList.add(searchModel)
                        }
                    }
                    adapter.updateList(searchList)
                }
                is DataState.Error -> {

                    Toast.makeText(requireContext(),it.exception.message,Toast.LENGTH_SHORT).show()
                }
                is DataState.Empty -> {

                    Toast.makeText(requireContext(),"Check Your Connection",Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val selectedItem = adapter.getItem(position) as BestMatches
            val bundle = Bundle()
            bundle.putString("ticker",selectedItem.symbol)
            bundle.putString("name",selectedItem.name)
            bundle.putString("currency",selectedItem.currency)
            findNavController().navigate(R.id.action_searchFragment_to_detailFragment,bundle)
        }
        return binding.root
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