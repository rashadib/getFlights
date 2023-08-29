package com.example.projectflights.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectflights.data.service.AirportService
import com.example.projectflights.data.service.FlightsService
import com.example.projectflights.data.service.dto.airports.Data
import com.example.projectflights.data.service.dto.flights.Itinerary
import kotlinx.coroutines.launch
import java.io.IOException
import java.time.LocalDate
import java.util.Date

import com.example.projectflights.data.service.dto.airports.Data as AirportData

class HomeViewModel : ViewModel() {

    var selectedOirignData: AirportData? = null
    var selectedDestinationData: AirportData? = null

    private val _originAirport = MutableLiveData<List<Data>>()
    val originAirport:LiveData<List<Data>> = _originAirport

    private val _destinationAirport = MutableLiveData<List<Data>>()
    val destinationAirport:LiveData<List<Data>> = _destinationAirport

    private val _flights = MutableLiveData<List<Itinerary>>()
    val flights: LiveData<List<Itinerary>> = _flights


    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _selectedDate = MutableLiveData<LocalDate>()
    val selectedDate: LiveData<LocalDate> = _selectedDate

    private val _noFlights = MutableLiveData(false)
    val noFlights: LiveData<Boolean> = _noFlights



    fun searchAirport(query: String?, airportDest: Boolean) {

        query?.let {
            if (query.isEmpty()) {
                _error.value = "No results"
                return
            }
            _error.value = null

            viewModelScope.launch {
                try {
                    _loading.value = true
                    val airportResponse =
                        AirportService.create().searchAirport(it)
                    if (!airportDest)
                        _originAirport.value = airportResponse.data
                    else
                        _destinationAirport.value = airportResponse.data



                    Log.d(
                        "RASHAD",
                        "retreveAirports-> skyid + entityId : ${_originAirport.value!![0].skyId + " " + _originAirport.value!![0].entityId + _destinationAirport.value!![0].skyId + " " + _destinationAirport.value!![0].entityId}"
                    )
                } catch (e: IOException) {
                    _error.value = e.message ?: "Please check your internet and try again"
                } catch (e: java.lang.Exception) {
                    _error.value = e.message ?: "Unknown Error"
                } finally {
                    _loading.value = false
                }
            }


        }
    }

    private suspend fun retrieveFlights(
        originSkyId: String,
        originEntityId: String,
        destinationSkyId: String,
        destinationEntityId: String,
        date: String
    ) {
        try {
            _noFlights.value = false
            _loading.value = true
            val flightResponse =
                FlightsService.create()
                    .getFlights(
                        originSkyId,
                        originEntityId,
                        destinationSkyId,
                        destinationEntityId,
                        date
                    )

            _flights.value = flightResponse.data.itineraries
//            Log.d(
//                "RASHAD",
//                "retreveFlights: ${flightResponse.data.itineraries[0].legs[0].arrival + " " + flightResponse.data.itineraries[0].legs[0].destination.city}"
//            )
        } catch (e: IOException) {
            Log.d("RASHAD", "retrieveFlights: error1")
            _error.value = e.message ?: "Please check your internet and try again"
        } catch (e: java.lang.Exception) {
            Log.d("RASHAD", "retrieveFlights: error2")
            _noFlights.value = true
            _error.value = e.message ?: "Unknown Error"
        } finally {
            Log.d("RASHAD", "retrieveFlights: error3")
            _loading.value = false
        }


    }

    suspend fun findFlights() {
        //TODO check all variables not null

        try {
            retrieveFlights(
                this@HomeViewModel.selectedOirignData?.skyId!!,
                this@HomeViewModel.selectedOirignData?.entityId!!,
                this@HomeViewModel.selectedDestinationData?.skyId!!,
                this@HomeViewModel.selectedDestinationData?.entityId!!,
                selectedDate.value!!.toString()
            )
        } catch (e: Exception) {
            Log.e("RASHAD", "${e.message}")
        }


    }

    fun updateSelectedDate(newDate: LocalDate) {
        _selectedDate.value = newDate
    }
}