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
        var list = arrayListOf<Itinerary>()
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            list = arguments?.getSerializable("flights", ArrayList<Itinerary>()::class.java)!!
        } else {
             list = arguments?.getSerializable("flights") as ArrayList<Itinerary>
        }
        var origin = arguments?.getString("origin")
        var destination = arguments?.getString("destination")

        binding.apply {
            list.apply {
                if(isEmpty()){
                    rvFlights.visibility = View.GONE
                    lottieNotFound.visibility = View.VISIBLE
                } else {
                    rvFlights.visibility = View.VISIBLE
                    lottieNotFound.visibility = View.GONE
                }
            }
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
//         Navigate to the FlightDetailsFragment and pass the "flight" as bundle.
        val bundle = Bundle()
        bundle.putParcelable("flight", flight)
        findNavController().navigate(R.id.action_flightListFragment_to_navigation_flightDetailsFragment, bundle)
    }


}