package com.example.projectflights.ui.home

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
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
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectflights.R
import com.example.projectflights.adapter.FlightsAdapter
import com.example.projectflights.data.service.dto.flights.Itinerary
import com.example.projectflights.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import com.example.projectflights.data.service.dto.airports.Data as AirportData


// The HomeFragment class represents the main UI screen for flight-search and presenting of the flights found(if any).
class HomeFragment : Fragment() {


    private var originDisplayText: String = ""
    private var destDisplayText: String = ""
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var shouldNavigate = false

    // Lists to store filtered airport data for origin and destination.
    private var filteredOriginList = arrayListOf<AirportData>()
    private var filteredDestinationList = arrayListOf<AirportData>()
    lateinit var homeViewModel: HomeViewModel

    // Create a handler to manage search delay.
    val searchHandler = Handler(Looper.getMainLooper())
    val searchDelay: Long = 700 // 3 second


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.searchAirportOrigin.setIconifiedByDefault(false)
        binding.searchAirportDestination.setIconifiedByDefault(false)

        // Set up search functionality for origin and destination airports.

        // Initialize lists to hold suggestions for origin and destination airports.
        val originAirportsList = ArrayList<Any?>()
        val destinationAirportsList = ArrayList<Any?>()


        // Create adapters for the suggestion lists.
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

        // Handle item click in the originAirports suggestion list.
        binding.originListView.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                homeViewModel.selectedOriginData = filteredOriginList[p2]
                val selectedItem = homeViewModel.selectedOriginData
                originDisplayText =
                    "${selectedItem?.skyId}, ${selectedItem?.presentation?.title} (${selectedItem?.presentation?.subtitle})"
                binding.searchAirportOrigin.setQuery(
                    originDisplayText,
                    false
                )
                // Clear and hide the suggestion list.
                filteredOriginList.clear()
                resetResultListView(SearchType.ORIGIN)
                binding.originListView.visibility = View.GONE
            }

        })

        // Handle item click in the destinationAirports suggestion list.
        binding.destListView.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                homeViewModel.selectedDestinationData = filteredDestinationList[p2]
                val selectedItem = homeViewModel.selectedDestinationData
                destDisplayText =
                    "${selectedItem?.skyId}, ${selectedItem?.presentation?.title} (${selectedItem?.presentation?.subtitle})"
                binding.searchAirportDestination.setQuery(
                    destDisplayText,
                    false
                )

                // Clear and hide the suggestion list.
                filteredDestinationList.clear()
                resetResultListView(SearchType.DEST)
                binding.destListView.visibility = View.GONE
                hideKeyBoard(p1!!)

            }

        })


        // Observe changes in origin airport data and update the suggestion list.
        homeViewModel.originAirport.observe(viewLifecycleOwner) {
            Log.d("TAG", "onCreateView: ")
            val listData = arrayListOf<String>()
            filteredOriginList.clear()
            it.map {
                listData.add("${it.skyId}, ${it.presentation.title} (${it.presentation.subtitle})")
                filteredOriginList.add(it)
            }
            // Update the adapter and show the suggestion list.
            originAdapter = ArrayAdapter<Any?>(
                requireActivity(),
                android.R.layout.simple_list_item_1,
                listData as List<String>
            )
            binding.originListView.adapter = originAdapter
            binding.originListView.visibility = View.VISIBLE

        }

        // Observe changes in destination airport data and update the suggestion list.
        homeViewModel.destinationAirport.observe(viewLifecycleOwner) {
            Log.d("TAG", "onCreateView: ")
            val listData = arrayListOf<String>()
            filteredDestinationList.clear()
            it.map {
                listData.add("${it.skyId}, ${it.presentation.title} (${it.presentation.subtitle})")
                filteredDestinationList.add(it)
            }
            // Update the adapter and show the suggestion list.
            destinationAdapter = ArrayAdapter<Any?>(
                requireActivity(),
                android.R.layout.simple_list_item_1,
                listData as List<String>
            )
            binding.destListView.adapter = destinationAdapter
            binding.destListView.visibility = View.VISIBLE

        }

        // Initialize suggestion lists as initially hidden.
        binding.originListView.visibility = View.GONE
        binding.originListView.adapter = originAdapter

        binding.destListView.visibility = View.GONE
        binding.destListView.adapter = destinationAdapter


        // Handle date selection when the "Choose Date" button is clicked.
        binding.etChooseDate.setOnClickListener {
            showDatePickerDialog(homeViewModel)
        }

        // Observe changes in the selected date and update the displayed date.
        homeViewModel.selectedDate.observe(viewLifecycleOwner) { selectedDate ->
            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val formattedDate = selectedDate.format(dateFormatter)
            binding.etChooseDate.setText(formattedDate)
        }

        // Handle the "Find Flights" button click.
        binding.btnFindFlights.setOnClickListener {
            // Hide the keyboard when the button is clicked.
            hideKeyBoard(it)

            // Start a coroutine to perform flight searches.
            shouldNavigate = true
            viewLifecycleOwner.lifecycleScope.launch {
                homeViewModel.findFlights()
            }
        }

        // Observe changes in flight data and update the RecyclerView.
        homeViewModel.flights.observe(viewLifecycleOwner) { it ->
            if (shouldNavigate) {
                val bundle = Bundle()
                bundle.putSerializable("flights", ArrayList(it))
                bundle.putString("origin", homeViewModel?.selectedOriginData?.skyId)
                bundle.putString("destination", homeViewModel?.selectedDestinationData?.skyId)
                findNavController().navigate(
                    R.id.action_navigation_home_to_flightListFragment,
                    bundle
                )
                shouldNavigate = false
            }
        }

        // Observe loading state and show/hide the loading indicator.
        homeViewModel.loading.observe(viewLifecycleOwner)
        {
            binding.lottieLoading.visibility = if (it) View.VISIBLE else View.GONE
        }

        // Observe the absence of flights and show/hide the corresponding UI elements.
        homeViewModel.noFlights.observe(viewLifecycleOwner)
        {
            binding.lottieLoading.visibility = View.GONE
        }

        return root
    }

    private fun hideKeyBoard(it: View) {
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(it.windowToken, 0)
    }


    override fun onResume() {
        super.onResume()
        //TODO Move destDisplayText and originDisplayText to viewModel
        binding.searchAirportDestination.setQuery(destDisplayText, false)
        binding.searchAirportOrigin.setQuery(originDisplayText, false)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    // Function that show a date picker dialog for selecting travel dates.
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
        // Set the minimum date to today's date (restricting past dates).
        datePicker.datePicker.minDate = System.currentTimeMillis() - 1000

        datePicker.show()
    }

    // Function that reset the suggestion lists based on search type (origin or destination).
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

    // Enum to distinguish between origin and destination searches.
    enum class SearchType {
        ORIGIN,
        DEST
    }


    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            searchAirportOrigin.outlineSpotShadowColor = Color.WHITE
            searchAirportOrigin.outlineAmbientShadowColor = Color.WHITE
        }
        // Handle text changes in the destination search view with a delay.
        binding.searchAirportDestination.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Remove any previous search tasks and schedule a new one with a delay.
                searchHandler.removeCallbacksAndMessages(null)
                searchHandler.postDelayed({
                    homeViewModel.searchAirport(newText, true)
                }, searchDelay)
                return true
            }

        })


        // Handle text changes in the origin search view.
        binding.searchAirportOrigin.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Remove any previous search tasks and schedule a new one with a delay.
                searchHandler.removeCallbacksAndMessages(null)
                searchHandler.postDelayed({
                    homeViewModel.searchAirport(newText, false)
                }, searchDelay)
                return true
            }

        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

