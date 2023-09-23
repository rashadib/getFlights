package com.example.projectflights.ui.flight_list

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectflights.R
import com.example.projectflights.adapter.FlightsAdapter
import com.example.projectflights.data.service.dto.flights.Itinerary
import com.example.projectflights.databinding.FragmentFlightDetailsBinding
import com.example.projectflights.databinding.FragmentFlightListBinding

//A fragment to display a list of flight itineraries.
class FlightListFragment : Fragment() {

    private var _binding: FragmentFlightListBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFlightListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve flight data from arguments.
        val list: ArrayList<Itinerary>
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            list = arguments?.getSerializable("flights", ArrayList<Itinerary>()::class.java)!!
        } else {
            list = arguments?.getSerializable("flights") as ArrayList<Itinerary>
        }
        var origin = arguments?.getString("origin")
        var destination = arguments?.getString("destination")

        binding.apply {
            // Determine visibility of flight list and "not found" animation based on the data.
            list.apply {
                if (isEmpty()) {
                    rvFlights.visibility = View.GONE
                    lottieNotFound.visibility = View.VISIBLE
                } else {
                    rvFlights.visibility = View.VISIBLE
                    lottieNotFound.visibility = View.GONE
                }
            }
            // Set up RecyclerView with flight data and item click listener.
            rvFlights.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = FlightsAdapter(list as List<Itinerary>) { item ->
                    onListItemClick(item)
                }
            }
            originTv.text = origin
            distinationTv.text = destination
        }
    }

    // Function that handles item click in the flight list to navigate to flight details.
    private fun onListItemClick(flight: Itinerary) {
        //Navigate to the FlightDetailsFragment and pass the "flight" as bundle.
        val bundle = Bundle()
        bundle.putParcelable("flight", flight)
        findNavController().navigate(
            R.id.action_flightListFragment_to_navigation_flightDetailsFragment,
            bundle
        )
    }


}