package com.raihan.story.presentation.fragment.story.add

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.raihan.story.R
import com.raihan.story.base.BaseFragment
import com.raihan.story.data.model.dto.network.ApiStatus
import com.raihan.story.databinding.FragmentStoryAddBinding
import com.raihan.story.utils.getImageUri
import com.raihan.story.utils.showToast
import com.yalantis.ucrop.UCrop
import org.koin.android.ext.android.inject
import java.io.File
import java.util.Date

class StoryAddFragment : BaseFragment<FragmentStoryAddBinding>() {
    override fun assignBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentStoryAddBinding {
        return FragmentStoryAddBinding.inflate(inflater, container, false)
    }

    private val viewModel: StoryAddViewModel by inject()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var lat: Double = 0.0
    private var long: Double = 0.0

    private var currentUri: Uri? = null
    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentUri = uri
            withUcrop(currentUri!!)
        }
    }
    private val launcherUCrop =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val resultUri = UCrop.getOutput(result.data!!)
                if (resultUri != null) {
                    currentUri = resultUri
                    setPreview()
                }
            }
        }

    private val launcherCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            withUcrop(currentUri!!)
        }
    }

    private fun withUcrop(uri: Uri) {
        val timestamp = Date().time
        val cachedImage =
            File(requireActivity().cacheDir, "cropped_image_${timestamp}.jpg")

        val destinationUri = Uri.fromFile(cachedImage)

        val uCrop = UCrop.of(uri, destinationUri).withAspectRatio(1f, 1f)

        uCrop.getIntent(requireContext()).apply {
            launcherUCrop.launch(this)
        }
    }

    private fun setPreview() {
        binding.ivPreviewStory.setImageURI(currentUri)
    }

    override fun doSomething() {
        super.doSomething()
        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())

        initListener()
        initObserver()
    }

    private fun initListener() {

        with(binding) {
            topBar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            cameraButton.setOnClickListener {
                currentUri = getImageUri(requireActivity())
                launcherCamera.launch(currentUri)
            }
            galleryButton.setOnClickListener {
                launcherGallery.launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            }
            buttonAdd.setOnClickListener {
                val description = edAddDescription.text.toString()

                if (description.isNotEmpty() && currentUri != null) {
                    viewModel.uploadStory(
                        currentUri!!, description, lat, long
                    )
                } else {
                    showToast(
                        requireActivity(),
                        getString(R.string.error_upload_story)
                    )
                }
            }
            switchLocation.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    getMyLastLocation()
                } else {
                    lat = 0.0
                    long = 0.0
                }
            }
        }

    }

    private fun initObserver() {
        viewModel.uploadStoryResult.observe(viewLifecycleOwner) { result ->
            binding.apply {
                when (result) {
                    is ApiStatus.Loading -> loadingButton.root.visibility =
                        View.VISIBLE

                    is ApiStatus.Success -> {
                        loadingButton.root.visibility = View.GONE
                        showToast(
                            requireActivity(), result.data.message
                        )
                        findNavController().popBackStack()
                    }

                    is ApiStatus.Error -> {
                        loadingButton.root.visibility = View.GONE
                        showToast(
                            requireActivity(), result.errorMessage
                        )
                    }

                    else -> loadingButton.root.visibility = View.GONE
                }
            }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false -> {
                // Precise location access granted.
                getMyLastLocation()
            }

            permissions[Manifest.permission.ACCESS_COARSE_LOCATION]
                ?: false -> {
                // Only approximate location access granted.
                getMyLastLocation()
            }

            else -> {
                // No location access granted.
            }
        }
    }

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            requireActivity(), permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun getMyLastLocation() {
        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) && checkPermission(
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    lat = location.latitude
                    long = location.longitude
                } else {
                    showToast(
                        requireActivity(),
                        "Location is not found. Try Again",
                    )
                }
            }
        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }
}