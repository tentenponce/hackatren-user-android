package com.nasaanka.ui.main

import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.nasaanka.R
import com.nasaanka.ui.base.BaseActivity
import javax.inject.Inject

class MainActivity : BaseActivity(), OnMapReadyCallback, MainMvpView {

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    private lateinit var mMap: GoogleMap

    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @Inject
    lateinit var mPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activityComponent()?.inject(this)
        mPresenter.attachView(this)

        init()
    }

    override fun onDestroy() {
        super.onDestroy()

        mPresenter.detachView()
    }

    override fun redirectToMapLocation(longitude: Double, latitude: Double) {
        val currentLatLng = LatLng(latitude, longitude)
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 13f))
    }

    private fun init() {
        /* init maps */
        (supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment).getMapAsync(this)

        /* init my location */
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        checkPermission()

        mMap.uiSettings.isZoomGesturesEnabled = true
        mMap.isMyLocationEnabled = true

        /* add listener for own location */
        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            if (location != null) {
                lastLocation = location

                mPresenter.setMyLocation(latitude = location.latitude, longitude = location.longitude)
            }
        }
    }

    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
    }
}
