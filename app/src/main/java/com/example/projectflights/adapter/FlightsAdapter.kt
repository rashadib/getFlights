package com.example.projectflights.adapter
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projectflights.data.service.dto.flights.Itinerary
import com.example.projectflights.databinding.FlightItemBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class FlightsAdapter(var flights: List<Itinerary>, private val onItemClick: (Itinerary) -> Unit) : RecyclerView.Adapter<FlightsAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(FlightItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = flights.size

    override fun onBindViewHolder(holder: VH, position: Int) {

        val flight = flights[position]


        with(holder.binding){
            tvOriginFlightResponse.text = flight.legs?.get(0)?.origin?.name
            tvDestinationFlightResponse.text = flight.legs?.get(0)?.destination?.name
            tvPriceResponse.text= flight.price.formatted
            tvItemAirline.text = flight.legs?.get(0)?.carriers?.marketing?.get(0)?.name
            flight.legs?.get(0)?.carriers?.marketing?.get(0)?.logoUrl?.let { logoUrl ->
                println(logoUrl)
                Picasso.get().load(logoUrl).into(ivItemFavicon, object : Callback{
                    override fun onSuccess() {
                        Log.d("RASHAD", "IMAGE-LOADING onSuccess: ")
                    }

                    override fun onError(e: Exception?) {
                        Log.e("RASHAD", "IMAGE-LOADING  onError: ${e?.message}" )
                    }

                })
            } ?: run {
                println("No Image for  ${flight.id}")
            }
            root.setOnClickListener {

                onItemClick(flight)

            }
        }
    }

    class VH(val binding: FlightItemBinding) : RecyclerView.ViewHolder(binding.root)
}