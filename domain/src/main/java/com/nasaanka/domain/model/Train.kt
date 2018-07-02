package com.nasaanka.domain.model

/**
 *
 * Created by Exequiel Egbert V. Ponce on 6/24/2018.
 */
data class Train(
        val name: String = "",
        val latitude: Double = 0.0,
        val longitude: Double = 0.0,
        val status: Int = RUNNING) {
    companion object {
        public const val RUNNING = 1
        public const val BROKEN = 2
    }
}