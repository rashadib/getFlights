package com.example.projectflights.ui.home

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
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
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectflights.R
import com.example.projectflights.adapter.FlightsAdapter
import com.example.projectflights.data.service.dto.flights.Eco
import com.example.projectflights.data.service.dto.flights.Itinerary
import com.example.projectflights.data.service.dto.flights.Leg
import com.example.projectflights.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import com.example.projectflights.data.service.dto.airports.Data as AirportData


class HomeFragment : Fragment() {


    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private var filteredOriginList = arrayListOf<AirportData>()
    private var filteredDestinationList = arrayListOf<AirportData>()

    @RequiresApi(Build.VERSION_CODES.O)
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


        val originAirportsList = ArrayList<Any?>()
        val destinationAirportsList = ArrayList<Any?>()

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
        binding.originListView.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Log.d("TAG", "onItemClick: ")
                //handle eshi
                homeViewModel.selectedOirignData = filteredOriginList[p2]
                val selectedItem = homeViewModel.selectedOirignData
                binding.searchAirportOrigin.setQuery(
                    "${selectedItem?.skyId}, ${selectedItem?.presentation?.title} (${selectedItem?.presentation?.subtitle})",
                    false
                )

                filteredOriginList.clear()
                resetResultListView(SearchType.ORIGIN)
                binding.originListView.visibility = View.GONE
            }

        })

        binding.destListView.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Log.d("TAG", "onItemClick: ")
                //handle eshi
                homeViewModel.selectedDestinationData = filteredDestinationList[p2]
                var selectedItem = homeViewModel.selectedDestinationData
                binding.searchAirportDestination.setQuery(
                    "${selectedItem?.skyId}, ${selectedItem?.presentation?.title} (${selectedItem?.presentation?.subtitle})",
                    false
                )

                filteredDestinationList.clear()
                resetResultListView(SearchType.DEST)
                binding.destListView.visibility = View.GONE
                Log.d("TAG", "onItemClick: here")

            }

        })


        binding.searchAirportOrigin.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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


        homeViewModel.originAirport.observe(viewLifecycleOwner) {
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
        homeViewModel.destinationAirport.observe(viewLifecycleOwner) {
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

        binding.searchAirportDestination.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
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

        binding.etChooseDate.setOnClickListener {
            showDatePickerDialog(homeViewModel)
        }
//
        homeViewModel.selectedDate.observe(viewLifecycleOwner) { selectedDate ->
            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val formattedDate = selectedDate.format(dateFormatter)
            binding.etChooseDate.setText(formattedDate)
        }

        binding.btnFindFlights.setOnClickListener {

            val imm =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(it.windowToken, 0)

            viewLifecycleOwner.lifecycleScope.launch {
                homeViewModel.findFlights()
            }
        }

        homeViewModel.flights.observe(viewLifecycleOwner) {
            with(binding.rvFlights) {
                layoutManager = LinearLayoutManager(context)
                adapter = FlightsAdapter(it) { item ->
                    onListItemClick(item)
                }
            }
        }


        homeViewModel.loading.observe(viewLifecycleOwner) {

            binding.progressLoading.visibility = if (it) View.VISIBLE else View.GONE
        }

        homeViewModel.noFlights.observe(viewLifecycleOwner){
            binding.ivNoFlights.visibility = if(it) View.VISIBLE else View.GONE
            binding.tvNoFlights.visibility = if(it) View.VISIBLE else View.GONE
        }

        return root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showDatePickerDialog(viewModel: HomeViewModel) {
        val initialYear = Calendar.getInstance().get(Calendar.YEAR)
        val initialMonth = Calendar.getInstance().get(Calendar.MONTH)
        val initialDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(
            requireContext(),
            { _, year, monthOfYear, dayOfMonth ->
                val selectedDate = LocalDate.of(year, monthOfYear + 1, dayOfMonth)
                viewModel.updateSelectedDate(selectedDate)
            },
            initialYear,
            initialMonth,
            initialDay
        )
        // Set the minimum date to today's date
        datePicker.datePicker.minDate = System.currentTimeMillis() - 1000

        datePicker.show()
    }

    private fun resetResultListView(type: SearchType) {
        val originAdapter = ArrayAdapter<Any?>(
            requireActivity(),
            android.R.layout.simple_list_item_1,
            arrayListOf()
        )
        if (type == SearchType.ORIGIN) {
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
        bundle.putParcelable("flight", flight)
        findNavController().navigate(R.id.action_navigation_home_to_flightDetailsFragment,bundle)

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchAirportOrigin.queryHint = resources.getString(R.string.origin_search_hint)
        binding.searchAirportDestination.queryHint =
            resources.getString(R.string.destination_search_hint)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

