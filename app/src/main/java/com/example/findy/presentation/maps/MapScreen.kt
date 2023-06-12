package com.example.findy.presentation.maps

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.findy.R
import com.example.findy.navigation.Screens
import com.example.findy.ui.theme.LocationUtils
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.CameraPosition
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import androidx.lifecycle.viewmodel.compose.viewModel



@SuppressLint("MissingPermission")
@Composable
fun MapScreen(fusedLocationProviderClient: FusedLocationProviderClient,
              navController: NavController) {

    val locationViewModel: LocationViewModel = viewModel()
    var currentLocation by remember { mutableStateOf(LocationUtils.getDefaultLocation()) }

    val cameraPositionState = rememberCameraPositionState()
    cameraPositionState.position = CameraPosition.fromLatLngZoom(
        LocationUtils.getPosition(currentLocation), 12f
    )

    var requestLocationUpdate by remember { mutableStateOf(true) }

    MyGoogleMap(
        currentLocation = currentLocation,
        cameraPositionState = cameraPositionState,
        onGpsIconClick = {
            requestLocationUpdate = true
        },
        onFriendsButtonClick = {
            navController.navigate(Screens.FriendsScreen.route)
        },
        locationViewModel = locationViewModel
    )

    if (requestLocationUpdate) {
        LocationPermissionsAndSettingDialogs(
            updateCurrentLocation = {
                requestLocationUpdate = false
                LocationUtils.requestLocationResultCallback(fusedLocationProviderClient) { locationResult ->
                    locationResult.lastLocation?.let { location ->
                        currentLocation = location
                    }
                }
            }
        )
    }
}

@Composable
private fun MyGoogleMap(
    currentLocation: Location?,
    cameraPositionState: CameraPositionState,
    onGpsIconClick: () -> Unit,
    onFriendsButtonClick: () -> Unit,
    locationViewModel: LocationViewModel = viewModel()
) {
    val mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(zoomControlsEnabled = false)
        )
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        uiSettings = mapUiSettings,
    ) {
        currentLocation?.let { location ->
            Marker(
                state = MarkerState(position = LocationUtils.getPosition(location)),
                title = "Aktuelle Position"
            )
        }
    }

    Box(Modifier.absoluteOffset(334.dp,764.dp) ){
        GpsIconButton(
        onIconClick = {
            onGpsIconClick()
            currentLocation?.let { location ->
                val userLocation = UserLocation(
                    latitude = location.latitude,
                    longitude = location.longitude
                )
                locationViewModel.saveUserLocation(userLocation) // Übergeben Sie die Location-Daten an das LocationViewModel
            }
        }
    )}

}

@Composable
private fun GpsIconButton(
    onIconClick: () -> Unit
) {

            IconButton(onClick = onIconClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_gps_fixed),
                    contentDescription = null,
                    Modifier.size(70.dp)

                    )
            }
        }

