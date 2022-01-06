package pl.edu.uj.ecommerce.Fragments

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import pl.edu.uj.ecommerce.R

class GoogleMapsFragment : Fragment() {

    private val callback = OnMapReadyCallback { googleMap ->

        val location1 = LatLng(50.056850, 19.952845)
        val location2 = LatLng(50.068575, 19.758743)
        val location3 = LatLng(49.973019, 19.806321)
        googleMap.addMarker(MarkerOptions().position(location1).title("Shop 1"))
        googleMap.addMarker(MarkerOptions().position(location2).title("Shop 2"))
        googleMap.addMarker(MarkerOptions().position(location3).title("Shop 3"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location1, 9.0f))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_google_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}