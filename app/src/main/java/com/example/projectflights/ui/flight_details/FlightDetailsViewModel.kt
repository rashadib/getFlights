package com.example.lec17.ui.movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class FlightDetailsViewModel: ViewModel() {
    private val _title = MutableLiveData<String?>()
    val title: LiveData<String?> = _title


}