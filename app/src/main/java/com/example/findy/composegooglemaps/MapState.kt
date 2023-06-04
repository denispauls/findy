package com.example.findy.composegooglemaps

import android.location.Location
import com.example.findy.composegooglemaps.clusters.ZoneClusterItem

data class MapState(
    val lastKnownLocation: Location?,
    val clusterItems: List<ZoneClusterItem>,
)