package com.raihan.story.presentation.fragment.map

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.raihan.story.R
import com.raihan.story.base.BaseFragment
import com.raihan.story.data.model.dto.network.ApiStatus
import com.raihan.story.databinding.FragmentMapsBinding
import com.raihan.story.utils.showToast
import org.koin.android.ext.android.inject

class MapsFragment : BaseFragment<FragmentMapsBinding>() {

    override fun assignBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentMapsBinding {
        return FragmentMapsBinding.inflate(inflater, container, false)
    }

    private val viewModel: MapsViewModel by inject()
    private val boundsBuilder = LatLngBounds.Builder()

    override fun doSomething() {
        super.doSomething()
        viewModel.getAllStoriesWithLocation()
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun setMapStyle(googleMap: GoogleMap) {
        try {
            val success = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    requireActivity(), R.raw.map_style
                )
            )
            if (!success) {
                showToast(requireActivity(), "Style parsing failed.")
            }
        } catch (exception: Resources.NotFoundException) {
            showToast(requireActivity(), "Can't find style. $exception")
        }
    }

    private fun setMapSettings(googleMap: GoogleMap) {
        googleMap.uiSettings.isZoomControlsEnabled = true
        googleMap.uiSettings.isIndoorLevelPickerEnabled = true
        googleMap.uiSettings.isCompassEnabled = true
        googleMap.uiSettings.isMapToolbarEnabled = true
    }


    private val callback = OnMapReadyCallback { googleMap ->
        setMapSettings(googleMap)
        setMapStyle(googleMap)

        viewModel.storyResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ApiStatus.Loading -> {
                    binding.viewLoading.visibility = View.VISIBLE
                }

                is ApiStatus.Success -> {
                    binding.progressBar.isIndeterminate = false
                    binding.progressBar.setProgress(100)
                    result.data.listStory.forEach { story ->
                        val latLng = LatLng(story.lat ?: 0.0, story.lon ?: 0.0)
                        googleMap.addMarker(
                            MarkerOptions().position(latLng).title(story.name)
                                .snippet(story.description)
                        )
                        boundsBuilder.include(latLng)
                    }

                    val bounds: LatLngBounds = boundsBuilder.build()
                    googleMap.animateCamera(
                        CameraUpdateFactory.newLatLngBounds(
                            bounds,
                            resources.displayMetrics.widthPixels,
                            resources.displayMetrics.heightPixels,
                            300
                        )
                    )
                    binding.viewLoading.visibility = View.GONE
                }

                is ApiStatus.Error -> {
                    binding.progressBar.isIndeterminate = false
                    binding.progressBar.setProgress(100)
                    showToast(
                        requireActivity(), result.errorMessage
                    )
                    binding.txtLoading.text = "Error"
                }

                else -> {}
            }

        }
    }

}