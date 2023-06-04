package com.example.findy.composegooglemaps.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

@Composable
fun MapScreen() {
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { context ->
                MapView(context).apply {
                    // Initialize the MapView
                    onCreate(null)
                    getMapAsync(OnMapReadyCallback { googleMap ->
                        // Custom initialization when the map is ready
                        googleMap.addMarker(
                            MarkerOptions().position(LatLng(49.1, -122.5)).title("Marker")
                        )
                        googleMap.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(LatLng(49.1, -122.5), 10f)
                        )
                    })
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}
