import android.annotation.SuppressLint
import android.location.Location
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.findy.R
import com.example.findy.navigation.Screens
import com.example.findy.ui.theme.LocationUtils
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.example.findy.presentation.maps.LocationPermissionsAndSettingDialogs
import com.example.findy.presentation.maps.LocationViewModel
import com.example.findy.presentation.maps.UserLocation
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@SuppressLint("MissingPermission")
@Composable
fun MapScreen(fusedLocationProviderClient: FusedLocationProviderClient,
              navController: NavController) {

    val locationViewModel: LocationViewModel = viewModel()
    var currentLocation by remember { mutableStateOf(LocationUtils.getDefaultLocation()) }

    val currentLocationState = remember { mutableStateOf<Location?>(null) }

    val cameraPositionState = rememberCameraPositionState()
    cameraPositionState.position = CameraPosition.fromLatLngZoom(
        LocationUtils.getPosition(currentLocation), 12f
    )

    var requestLocationUpdate by remember { mutableStateOf(true) }



    LaunchedEffect(Unit) {
        while (isActive) {
            LocationUtils.requestLocationResultCallback(fusedLocationProviderClient) { locationResult ->
                locationResult.lastLocation?.let { location ->
                    locationViewModel.updateCurrentLocation(location)
                    currentLocationState.value = location
                    val userLocation = UserLocation(
                        latitude = location.latitude,
                        longitude = location.longitude
                    )
                    locationViewModel.saveUserLocation(userLocation)
                }
            }
            delay(2000) // Warten für zwei Sekunden
        }
    }

    MyGoogleMap(
        currentLocationState = currentLocationState,
        cameraPositionState = cameraPositionState,
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
    currentLocationState: MutableState<Location?>,
    cameraPositionState: CameraPositionState,
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
        currentLocationState.value?.let { location ->
            Marker(
                state = MarkerState(position = LocationUtils.getPosition(location)),
                title = "Aktuelle Position"
            )
        }
    }

    GpsIconButton(
        onIconClick = {
            currentLocationState.value?.let { location ->
                val userLocation = UserLocation(
                    latitude = location.latitude,
                    longitude = location.longitude
                )
                locationViewModel.saveUserLocation(userLocation)
            }
        },
        onFriendsButtonClick = onFriendsButtonClick
    )
}


@Composable
private fun GpsIconButton(
    onIconClick: () -> Unit,
    onFriendsButtonClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = onIconClick) {
                Icon(
                    modifier = Modifier.padding(bottom = 100.dp, end = 20.dp),
                    painter = painterResource(id = R.drawable.ic_gps_fixed),
                    contentDescription = null
                )
            }
        }
    }
}

