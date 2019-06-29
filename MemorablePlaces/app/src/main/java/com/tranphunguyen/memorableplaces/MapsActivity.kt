package com.tranphunguyen.memorableplaces

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    var dialogIsOpen = false
    var placeGet: Place? = null
    private lateinit var mMap: GoogleMap
    private lateinit var mLocationManager: LocationManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        snackbarCustom.translationY = 500f

        placeGet = intent.getParcelableExtra("place")

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                val sydney = LatLng(-34.0, 151.0)
                mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

            }
        }
    }

    fun getAddress(latLng: LatLng): String {

        val geocoder = Geocoder(this)

        val listAddress = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
        var result = ""
        if (listAddress != null) {
            val address = listAddress[0]

            for (i in 0 until address.maxAddressLineIndex) {
                result += address.getAddressLine(i) + ", "
            }
            result += address.getAddressLine(address.maxAddressLineIndex)
        }

        return result
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mLocationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        mMap.setOnMapLongClickListener { latLng ->
            val address = getAddress(latLng)

            Log.d("TestPlace", address)

            if (!dialogIsOpen) {
                snackbarCustom.animate().translationYBy(-500f).duration = 300
                dialogIsOpen = true
            }

            snackbarCancel.setOnClickListener {

                snackbarCustom.animate().translationYBy(500f).duration = 300
                dialogIsOpen = false
            }

            snackbarOk.setOnClickListener {

                snackbarCustom.animate().translationYBy(500f).duration = 300

                val place = Place(address, latLng.latitude, latLng.longitude)

                SQLHelper(this@MapsActivity).addPlace(place)

                Snackbar.make(rootContent, "Thêm thành công!", Snackbar.LENGTH_SHORT).show()

                dialogIsOpen = false
            }

            tvAdress.text = address

            mMap.clear()
            mMap.addMarker(MarkerOptions().position(latLng).title("You Location!"))
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(it,10.0f))
        }

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)

            ActivityCompat.requestPermissions(this, permissions, 1)

        } else {

            val lastKnowLocation: Location? = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

            if (lastKnowLocation != null) {
                val yourLocation = LatLng(lastKnowLocation.latitude, lastKnowLocation.longitude)

                mMap.addMarker(MarkerOptions().position(yourLocation).title("You Location!"))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(yourLocation, 10.0f))
            } else {

                var latLng = LatLng(10.838033, 106.800503)
                var title = ""
                placeGet?.let {
                    latLng = LatLng(it.latitude, it.longitude)
                    title = it.name
                }

                mMap.addMarker(MarkerOptions().position(latLng).title(title))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20.0f))
            }


        }
    }
}
