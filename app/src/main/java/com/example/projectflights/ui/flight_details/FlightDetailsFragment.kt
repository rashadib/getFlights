package com.example.lec17.ui.movie_details

import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.example.projectflights.data.service.dto.flights.Itinerary
import com.example.projectflights.databinding.FragmentFlightDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FlightDetailsFragment : Fragment() {
    private var _binding: FragmentFlightDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    private lateinit var viewModel: FlightDetailsViewModel

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {  val homeViewModel =
        ViewModelProvider(this).get(FlightDetailsViewModel::class.java)

        _binding = FragmentFlightDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val flight = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            arguments?.getParcelable("flight", Itinerary::class.java) ?: return
        }else{
            arguments?.getParcelable("flight") ?: return
        }


    }


}