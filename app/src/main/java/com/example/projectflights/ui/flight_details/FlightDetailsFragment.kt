package com.example.lec17.ui.movie_details

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.projectflights.data.service.dto.flights.Itinerary
import com.example.projectflights.databinding.FragmentFlightDetailsBinding
import com.squareup.picasso.Picasso
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class FlightDetailsFragment : Fragment() {
    private var _binding: FragmentFlightDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    private lateinit var viewModel: FlightDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Toast.makeText(requireContext(), "AAA", Toast.LENGTH_SHORT).show()
        return false
    }

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



    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        val flight = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            arguments?.getParcelable("flight", Itinerary::class.java) ?: return
        }else{
            arguments?.getParcelable("flight") ?: return
        }

        with(binding){
            val departureTime = flight.legs?.get(0)?.departure
            val arrivalTime = flight.legs?.get(0)?.arrival



            val formattedDepartureTime =
                departureTime?.let {
                    formatDate(it,"HH:mm")
                }

            val formattedDepartureDate =
                departureTime?.let {
                    formatDate(it,"dd-MM-yyyy")
                }

            val formattedArrivalTime =
                arrivalTime?.let {
                    formatDate(it,"HH:mm")
                }

            val formattedArrivalDate =
                arrivalTime?.let {
                    formatDate(it,"dd-MM-yyyy")
                }

            tvAirlineName.text = flight.legs?.get(0)?.carriers?.marketing?.get(0)?.name ?: "not found"
            tvDepartureTime.text= formattedDepartureTime
            tvFdOriginAirport.text = flight.legs?.get(0)?.origin?.displayCode
            tvArrivalTime.text = formattedArrivalTime
            tvArrivalAirport.text = flight.legs?.get(0)?.destination?.displayCode
            tvDepartureAirportName.text = flight.legs?.get(0)?.origin?.name
            tvArrivalAirportName.text = flight.legs?.get(0)?.destination?.name
            tvDepartureDate.text = formattedDepartureDate
            tvArrivalDate.text = formattedArrivalDate
            tvPrice.text = flight.price.formatted

            Picasso.get().load(flight.legs?.get(0)?.carriers?.marketing?.get(0)?.logoUrl).into(ivAirlineFavicom)

            val flightDuration = flight.legs?.get(0)?.durationInMinutes
            val flightDurationHours = flightDuration?.div(60)
            val flightDurationMinutes = flightDuration?.rem(60)

            if (flightDuration != null) {
                if(flightDuration >= 60){
                    tvFlightDuration.text= "${flightDurationHours.toString()}:${flightDurationMinutes.toString()} hours"
                }else{
                    tvFlightDuration.text= "${flightDuration.toString()} minutes"
                }

            }
            val timeDeltaDays =  flight.legs?.get(0)?.timeDeltaInDays
            if (timeDeltaDays != null) {
                if(timeDeltaDays >0 ){
                    tvTimeDeltaDays.visibility = View.VISIBLE
                    tvTimeDeltaDays.text = "+(${timeDeltaDays} day)"
                }else{
                    tvTimeDeltaDays.visibility = View.GONE
                }
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun formatDate(dateString: String, pattern: String): String {
        val parsedDate = LocalDateTime.parse(dateString, DateTimeFormatter.ISO_DATE_TIME)
        return parsedDate.format(DateTimeFormatter.ofPattern(pattern))
    }



}