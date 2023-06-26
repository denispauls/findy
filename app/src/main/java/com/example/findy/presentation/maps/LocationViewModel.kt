package com.example.findy.presentation.maps

import android.location.Location
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findy.ui.theme.LocationUtils.isLocationPermissionGranted
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
class LocationViewModel @Inject constructor() : ViewModel() {
    private val db = Firebase.firestore

    private val _currentLocation = MutableLiveData<Location>()
    val currentLocation: LiveData<Location>
        get() = _currentLocation

    fun updateCurrentLocation(location: Location) {
        _currentLocation.value = location
    }

    fun saveUserLocation(userLocation: UserLocation) {
        val userId = Firebase.auth.currentUser?.uid ?: return
        val userLocationRef = db.collection("users").document(userId)

        userLocationRef.set(userLocation)
            .addOnSuccessListener {

            }
            .addOnFailureListener { exception ->

            }
    }
}