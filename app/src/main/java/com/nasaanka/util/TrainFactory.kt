package com.nasaanka.util

import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.nasaanka.R
import com.nasaanka.domain.model.Train

class TrainFactory {

    companion object {
        fun buildTrainMarker(train: Train): MarkerOptions =
                MarkerOptions()
                        .position(LatLng(train.latitude, train.longitude))
                        .title(train.name)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.train_location))
    }
}