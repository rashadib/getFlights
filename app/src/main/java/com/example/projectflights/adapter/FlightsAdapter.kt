package com.example.projectflights.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projectflights.data.service.dto.flights.Itinerary
import com.example.projectflights.databinding.FlightItemBinding

class FlightsAdapter(val flights: List<Itinerary>, private val onItemClick: (Itinerary) -> Unit) : RecyclerView.Adapter<FlightsAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(FlightItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = flights.size

    override fun onBindViewHolder(holder: VH, position: Int) {

        val flight = flights[position]


        with(holder.binding){

            tvOriginFlightResponse.text = flight.legs[0].origin.name
            tvDestinationFlightResponse.text = flight.legs[0].destination.name
            tvPriceResponse.text= flight.price.formatted

            root.setOnClickListener {

                onItemClick(flight)

            }
        }
    }

    class VH(val binding: FlightItemBinding) : RecyclerView.ViewHolder(binding.root)
}