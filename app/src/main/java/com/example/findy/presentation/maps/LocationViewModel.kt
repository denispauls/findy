package com.example.findy.presentation.maps

import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor() : ViewModel() {
    private val db = Firebase.firestore

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