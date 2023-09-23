package com.example.projectflights.ui.flight_details

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
import com.example.lec17.ui.movie_details.FlightDetailsViewModel
import com.example.projectflights.data.service.dto.flights.Itinerary
import com.example.projectflights.databinding.FragmentFlightDetailsBinding
import com.squareup.picasso.Picasso
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class FlightDetailsFragment : Fragment() {
    private var _binding: FragmentFlightDetailsBinding? = null

    private val binding get() = _binding!!

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Toast.makeText(requireContext(), "AAA", Toast.LENGTH_SHORT).show()
        return false
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        ViewModelProvider(this)[FlightDetailsViewModel::class.java]

        _binding = FragmentFlightDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        val flight = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable("flight", Itinerary::class.java) ?: return
        } else {
            arguments?.getParcelable("flight") ?: return
        }

        with(binding) {

            tvAirlineName.text =
                flight.legs?.get(0)?.carriers?.marketing?.get(0)?.name ?: "not found"
            tvDepartureTime.text = flight.legs?.get(0)?.departure?.let { formatDate(it, "HH:mm") }
            tvFdOriginAirport.text = flight.legs?.get(0)?.origin?.displayCode
            tvArrivalTime.text = flight.legs?.get(0)?.arrival?.let { formatDate(it, "HH:mm") }
            tvArrivalAirport.text = flight.legs?.get(0)?.destination?.displayCode
            tvDepartureAirportName.text = flight.legs?.get(0)?.origin?.name
            tvArrivalAirportName.text = flight.legs?.get(0)?.destination?.name
            tvDepartureDate.text =
                flight.legs?.get(0)?.departure?.let { formatDate(it, "dd-MM-yyyy") }
            tvArrivalDate.text = flight.legs?.get(0)?.arrival?.let { formatDate(it, "dd-MM-yyyy") }
            tvPrice.text = flight.price.formatted

            val stops = flight.legs?.get(0)?.stopCount

            if ((stops != null) && (stops > 0)) {
                binding.tvStops.visibility = View.VISIBLE
                binding.tvStops.text = "($stops stops)"
            } else {
                binding.tvStops.visibility = View.GONE
                binding.line2Iv.visibility = View.GONE
            }



            Picasso.get().load(flight.legs?.get(0)?.carriers?.marketing?.get(0)?.logoUrl)
                .into(ivAirlineFavicom)

            val flightDuration = flight.legs?.get(0)?.durationInMinutes
            tvFlightDuration.text = formatFlightDuration(flightDuration)

            val timeDeltaDays = flight.legs?.get(0)?.timeDeltaInDays
            with(tvTimeDeltaDays) {
                if (timeDeltaDays != null && timeDeltaDays > 0) {
                    visibility = View.VISIBLE
                    text = "(+${timeDeltaDays} day)"
                } else {
                    View.GONE
                }
            }

        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun formatDate(dateString: String, pattern: String): String {
        val parsedDate = LocalDateTime.parse(dateString, DateTimeFormatter.ISO_DATE_TIME)
        return parsedDate.format(DateTimeFormatter.ofPattern(pattern))
    }

    private fun formatFlightDuration(minutes: Int?): String {
        return if (minutes != null) {
            val hours = minutes / 60
            val remainingMinutes = minutes % 60
            if (minutes >= 60) {
                "${hours}h:${remainingMinutes}m"
            } else {
                "${minutes}m"
            }
        } else {
            ""
        }
    }


}