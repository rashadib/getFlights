package com.example.projectflights.ui.home

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectflights.R
import com.example.projectflights.adapter.FlightsAdapter
import com.example.projectflights.data.service.dto.flights.Itinerary
import com.example.projectflights.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch
import com.example.projectflights.data.service.dto.airports.Data as AirportData


class HomeFragment : Fragment() {



        
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private var filteredOriginList = arrayListOf<AirportData>()
    private var filteredDestinationList = arrayListOf<AirportData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.searchAirportOrigin.setIconifiedByDefault(false)
        binding.searchAirportDestination.setIconifiedByDefault(false)
//        binding.searchAirportOrigin.setOnQueryTextFocusChangeListener { view, hasFocus ->
//            if (hasFocus) {
//                binding.searchAirportOrigin.setIconified(false)
//            }
//        }

        var originAirportsList = ArrayList<Any?>()
        var destinationAirportsList = ArrayList<Any?>()

        val searchHandler = Handler(Looper.getMainLooper())
        val searchDelay: Long = 1000 // 1 second


        var originAdapter = ArrayAdapter<Any?>(
            requireActivity(),
            android.R.layout.simple_list_item_1,
            originAirportsList
        )
        var destinationAdapter = ArrayAdapter<Any?>(
            requireActivity(),
            android.R.layout.simple_list_item_1,
            destinationAirportsList
        )
        binding.originListView.setOnItemClickListener(object : OnItemClickListener{
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Log.d("TAG", "onItemClick: ")
                //handle eshi
                homeViewModel.selectedOirignData = filteredOriginList[p2]
                var selectedItem = homeViewModel.selectedOirignData
                binding.searchAirportOrigin.setQuery("${selectedItem?.skyId}, ${selectedItem?.presentation?.title} (${selectedItem?.presentation?.subtitle})", false)

                filteredOriginList.clear()
                resetResultListView(SearchType.ORIGIN)
                binding.originListView.visibility = View.GONE
            }

        })

        binding.destListView.setOnItemClickListener(object : OnItemClickListener{
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Log.d("TAG", "onItemClick: ")
                //handle eshi
                homeViewModel.selectedDestinationData = filteredDestinationList[p2]
                var selectedItem = homeViewModel.selectedDestinationData
                binding.searchAirportDestination.setQuery("${selectedItem?.skyId}, ${selectedItem?.presentation?.title} (${selectedItem?.presentation?.subtitle})", false)

                filteredDestinationList.clear()
                resetResultListView(SearchType.DEST)
                binding.destListView.visibility = View.GONE
                Log.d("TAG", "onItemClick: here")

            }

        })


        binding.searchAirportOrigin.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
               // homeViewModel.searchAirport(query,false)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                homeViewModel.searchAirport(newText, false)
                binding.originListView.visibility = View.VISIBLE

                return true
            }

        })


        homeViewModel.originAirport.observe(viewLifecycleOwner){
            Log.d("TAG", "onCreateView: ")
            var listData = arrayListOf<String>()
            filteredOriginList.clear()
            it.map {
                listData.add("${it.skyId}, ${it.presentation.title} (${it.presentation.subtitle})")
                filteredOriginList.add(it)
            }
            originAdapter = ArrayAdapter<Any?>(
                requireActivity(),
                android.R.layout.simple_list_item_1,
                listData as List<String>
            )
            binding.originListView.adapter = originAdapter
            binding.originListView.visibility = View.VISIBLE

        }
        homeViewModel.destinationAirport.observe(viewLifecycleOwner){
            Log.d("TAG", "onCreateView: ")
            var listData = arrayListOf<String>()
            filteredDestinationList.clear()
            it.map {
                listData.add("${it.skyId}, ${it.presentation.title} (${it.presentation.subtitle})")
                filteredDestinationList.add(it)
            }
            destinationAdapter = ArrayAdapter<Any?>(
                requireActivity(),
                android.R.layout.simple_list_item_1,
                listData as List<String>
            )
            binding.destListView.adapter = destinationAdapter
            binding.destListView.visibility = View.VISIBLE

        }

        binding.originListView.visibility = View.GONE
        binding.originListView.adapter = originAdapter

        binding.destListView.visibility = View.GONE
        binding.destListView.adapter = destinationAdapter

        binding.searchAirportDestination.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                //homeViewModel.searchAirport(query, true)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchHandler.removeCallbacksAndMessages(null)
                // Schedule a new search operation with a delay
                searchHandler.postDelayed({
                    homeViewModel.searchAirport(newText, true)
                }, searchDelay)
                return true
            }

        })

        binding.etChooseDate.addTextChangedListener { editable ->
            homeViewModel.updateDate(editable.toString())
        }

        binding.btnFindFlights.setOnClickListener{

            val imm = requireActivity().getSystemService( Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(it.windowToken, 0)

            viewLifecycleOwner.lifecycleScope.launch {
                homeViewModel.findFlights()
                //clearSearch()
            }
        }

        homeViewModel.flights.observe(viewLifecycleOwner) {
            with( binding.rvFlights){
                layoutManager = LinearLayoutManager(context)
                adapter = FlightsAdapter(it){ item ->
                    onListItemClick(item)
                }
            }
        }

//        homeViewModel.error.observe(viewLifecycleOwner){
//            if(it !=null) {
//                with(binding.textError) {
//                    visibility = View.VISIBLE
//                    text = it
//                }
//            }else{
//                binding.textError.visibility = View.GONE
//            }
//        }

        homeViewModel.loading.observe(viewLifecycleOwner){

            binding.progressLoading.visibility =  if(it) View.VISIBLE else View.GONE
        }

        return root
    }

    private fun resetResultListView(type: SearchType) {
        var originAdapter = ArrayAdapter<Any?>(
            requireActivity(),
            android.R.layout.simple_list_item_1,
            arrayListOf()
        )
        if(type == SearchType.ORIGIN) {
            binding.originListView.adapter = originAdapter
        } else {
            binding.originListView.adapter = originAdapter
        }
    }

    enum class SearchType {
        ORIGIN,
        DEST
    }

    private fun onListItemClick(flight: Itinerary) {

        val bundle = Bundle()
        bundle.putParcelable("flight",flight)
     //   findNavController().navigate(R.id.action_navigation_home_to_flightDetailsFragment)

    }

    private fun clearSearch() {
        binding.searchAirportOrigin.apply {
            setQuery("", false);
            clearFocus();
            onActionViewCollapsed();
        }
        binding.searchAirportDestination.apply {
            setQuery("", false);
            clearFocus();
            onActionViewCollapsed();
        }
        binding.etChooseDate.text.clear()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchAirportOrigin.queryHint = resources.getString(R.string.origin_search_hint)
        binding.searchAirportDestination.queryHint = resources.getString(R.string.destination_search_hint)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

