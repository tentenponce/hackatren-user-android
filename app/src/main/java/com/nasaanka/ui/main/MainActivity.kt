package com.nasaanka.ui.main

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.nasaanka.R
import com.nasaanka.domain.model.Train
import com.nasaanka.ui.base.BaseActivity
import com.nasaanka.util.TrainFactory
import javax.inject.Inject

class MainActivity : BaseActivity(), OnMapReadyCallback, MainMvpView {

    companion object {

        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    private lateinit var mMap: GoogleMap
    private var trainMarkers: MutableMap<String, Marker> = hashMapOf()

    private var fusedLocationClient: FusedLocationProviderClient? = null
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    @Inject
    lateinit var mPresenter: MainPresenter

    private var hasPermission: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activityComponent()?.inject(this)
        mPresenter.attachView(this)

        hasPermission = hasPermission()
        init()
        mPresenter.getTrainLocations()
    }

    override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    override fun onDestroy() {
        super.onDestroy()

        mPresenter.detachView()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        var index = 0
        val PermissionsMap = HashMap<String, Int>()
        for (permission in permissions) {
            PermissionsMap[permission] = grantResults[index]
            index++
        }

        hasPermission = PermissionsMap.get(android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED

        if (hasPermission) {
            recreate()
        }
    }

    override fun redirectToMapLocation(longitude: Double, latitude: Double) {
        val currentLatLng = LatLng(latitude, longitude)
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 14f))
    }

    override fun updateTrainMarker(train: Train) {
        val trainMarker: Marker? = trainMarkers.get(train.name) // get marker

        if (trainMarker != null) { // check if specific train marker already exists on the map
            trainMarker.position = LatLng(train.latitude, train.longitude)
            trainMarker.setIcon(TrainFactory.getStatusIcon(train.status))
        } else { // else add it on the map
            val marker = mMap.addMarker(TrainFactory.buildTrainMarker(train))

            trainMarkers.put(train.name, marker)
        }
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomGesturesEnabled = true

        if (hasPermission) {
            mMap.isMyLocationEnabled = true

            /* add listener for own location */
            fusedLocationClient?.lastLocation?.addOnSuccessListener(this) { location ->
                if (location != null) {
                    mPresenter.setMyLocation(latitude = location.latitude, longitude = location.longitude)
                }
            }
        }
    }

    private fun init() {
        /* init maps */
        (supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment).getMapAsync(this)

        /* init location updates */
        locationRequest = LocationRequest().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations) {
                    mPresenter.setMyLocation(latitude = location.latitude, longitude = location.longitude)
                }
            }
        }

        /* init my location */
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        startLocationUpdates()
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        if (hasPermission) {
            fusedLocationClient?.requestLocationUpdates(locationRequest,
                    locationCallback,
                    null)
        }
    }

    private fun stopLocationUpdates() {
        fusedLocationClient?.removeLocationUpdates(locationCallback)
    }

    private fun hasPermission(): Boolean {
        val hasPermission: Boolean = ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

        if (!hasPermission) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
        }

        return hasPermission
    }
}
