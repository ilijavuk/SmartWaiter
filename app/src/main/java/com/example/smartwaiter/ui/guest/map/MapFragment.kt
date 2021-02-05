package com.example.smartwaiter.ui.guest.map


import android.Manifest
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.example.smartwaiter.R
import com.example.smartwaiter.repository.Add_mealRepository
import com.example.smartwaiter.ui.restaurant.editMeal.EditMealModelFactory
import com.example.smartwaiter.ui.restaurant.editMeal.EditMealViewModel
import com.example.smartwaiter.util.handleApiError
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import hr.foi.air.webservice.model.Restoran
import hr.foi.air.webservice.model.Tag
import hr.foi.air.webservice.util.Resource


class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var locationManager : LocationManager? = null
    private lateinit var viewModel: MapViewModel
    var already_loaded = false

    companion object {
        var mapFragment : SupportMapFragment?=null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        load()
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.fragment_map, container, false)
        locationManager = activity?.getSystemService(LOCATION_SERVICE) as LocationManager?



        mapFragment = childFragmentManager?.findFragmentById(R.id.fallasMap) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        return rootView

    }
    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            if(!already_loaded) {
                val sydney = LatLng(location.latitude, location.longitude)
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
                mMap?.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(
                            location!!.latitude,
                            location!!.longitude
                        ), 15F
                    )
                )
            }
            already_loaded=true

        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    fun load(){
        val repository = Add_mealRepository()
        val viewModelFactory = MapModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MapViewModel::class.java)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap!!
        if (activity?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED && activity?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED
        ) {

        }
        mMap.setMyLocationEnabled(true);
        locationManager?.requestLocationUpdates(
            LocationManager.NETWORK_PROVIDER,
            0L,
            0f,
            locationListener
        )
        mMap?.uiSettings?.isMyLocationButtonEnabled = true 

        SetMarkers()

    }

    private fun GetRestaurants(){
        viewModel.getRestorani("Lokal", "select")
    }
    private fun SetMarkers(){
        GetRestaurants()
        viewModel.myResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    val listRestaurants: MutableList<Restoran> = response.value as MutableList<Restoran>
                    for(restoran in listRestaurants){
                        val restaurant = LatLng(restoran.GPS_longitude, restoran.GPS_latitude)
                        val height = 130
                        val width = 130
                        val bitmapdraw = resources.getDrawable(R.drawable.logo_map_pin) as BitmapDrawable
                        val b = bitmapdraw.bitmap
                        val smallMarker = Bitmap.createScaledBitmap(b, width, height, false)

                        mMap.addMarker(
                            MarkerOptions()
                                .position(restaurant)
                                .title(restoran.naziv)
                                .snippet(restoran.adresa)
                                .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))

                        )


                    }

                }
                is Resource.Loading -> {
                }
                is Resource.Failure -> {
                    handleApiError(response) { GetRestaurants() }
                }
            }
        })
    }

}
