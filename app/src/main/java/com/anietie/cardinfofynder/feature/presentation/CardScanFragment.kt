package com.anietie.cardinfofynder.feature.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.anietie.cardinfofynder.R
import com.anietie.cardinfofynder.databinding.FragmentCardScanBinding
import com.getbouncer.cardscan.ui.CardScanFlow
import com.getbouncer.scan.camera.CameraAdapter
import com.getbouncer.scan.camera.CameraErrorListener
import com.getbouncer.scan.camera.CameraPreviewImage
import com.getbouncer.scan.camera.getCameraAdapter
import com.getbouncer.scan.framework.AnalyzerLoopErrorListener
import com.getbouncer.scan.framework.Config.logTag
import com.getbouncer.scan.framework.Stats
import com.getbouncer.scan.framework.Stats.instanceId
import com.getbouncer.scan.framework.Stats.scanId
import com.getbouncer.scan.framework.api.dto.ScanStatistics
import com.getbouncer.scan.framework.api.uploadScanStats
import com.getbouncer.scan.framework.util.AppDetails
import com.getbouncer.scan.ui.util.asRect
import java.util.*

class CardScanFragment() : Fragment(), CameraErrorListener, AnalyzerLoopErrorListener {

    private val PERMISSION_REQUEST_CODE = 1200
    private val MINIMUM_RESOLUTION = Size(1280, 720)

    private var _binding: FragmentCardScanBinding? = null
    private val binding get() = _binding!!

    private lateinit var cameraAdapter: CameraAdapter<CameraPreviewImage<Bitmap>>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
//        setStateNotFound()
    }

    /**
     * Request permission to use the camera.
     */
    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(), arrayOf(Manifest.permission.CAMERA),
            PERMISSION_REQUEST_CODE
        )
    }

    /**
     * Handle permission status changes. If the camera permission has been granted, start it. If
     * not, show a dialog.
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                startScan()
            } else {
                showPermissionDeniedDialog()
            }
        }
    }

    /**
     * Show an explanation dialog for why we are requesting camera permissions.
     */
    private fun showPermissionDeniedDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(R.string.bouncer_camera_permission_denied_message)
            .setPositiveButton(
                R.string.bouncer_camera_permission_denied_ok
            ) { dialog, which -> requestCameraPermission() }
            .setNegativeButton(
                R.string.bouncer_camera_permission_denied_cancel
            ) { dialog, which ->  }
            .show()
    }

    /**
     * Start the scanning flow.
     */
//    private fun startScan() {
//        // ensure the cameraPreview view has rendered.
//        with(binding){
//            cameraPreviewHolder.post(Runnable {
//
//                // Track scan statistics for health check
//                Stats.startScan()
//
//                // Tell the background where to draw a hole for the viewfinder window
//                viewFinderBackground.setViewFinderRect(viewFinderWindow.asRect())
//
//                // Create a camera adapter and bind it to this activity.
//                cameraAdapter = getCameraAdapter(
//                    requireActivity(),
//                    cameraPreview,
//                    MINIMUM_RESOLUTION,
//                    this
//                )
//                cameraAdapter.bindToLifecycle(this)
//                cameraAdapter.withFlashSupport { supported: Boolean ->
//                    flashButtonView.setVisibility(if (supported) View.VISIBLE else View.INVISIBLE)
//                    Unit
//                }
//
//                // Create and start a CardScanFlow which will handle the business logic of the scan
//                cardScanFlow = CardScanFlow(
//                    true,
//                    true,
//                    aggregateResultListener,
//                    this
//                )
//                cardScanFlow.startFlow(
//                    this,
//                    cameraAdapter.getImageStream(),
//                    viewFinderWindow.asRect(),
//                    this,
//                    this
//                )
//            })
//        }
//    }


    /**
     * Turn the flashlight on or off.
     */
//    private fun setFlashlightState(on: Boolean) {
//        if (cameraAdapter != null) {
//            cameraAdapter.setTorchState(on)
//            if (cameraAdapter.isTorchOn()) {
//                flashButtonView.setImageResource(R.drawable.bouncer_flash_on_dark)
//            } else {
//                flashButtonView.setImageResource(R.drawable.bouncer_flash_off_dark)
//            }
//        }
//    }

    /**
     * Cancel scanning due to analyzer failure
     */
    private fun analyzerFailureCancelScan(cause: Throwable?) {
        Log.e(logTag, "Canceling scan due to analyzer error", cause)
        AlertDialog.Builder(requireContext())
            .setMessage("Analyzer failure")
            .show()
//        closeScanner()
    }

    /**
     * Cancel scanning due to a camera error.
     */
    private fun cameraErrorCancelScan(cause: Throwable?) {
        Log.e(logTag, "Canceling scan due to camera error", cause)
        AlertDialog.Builder(requireContext())
            .setMessage("Camera error")
            .show()
//        closeScanner()
    }

    /**
     * The scan has been cancelled by the user.
     */
    private fun userCancelScan() {
        AlertDialog.Builder(requireContext())
            .setMessage("Scan Canceled by user")
            .show()
//        closeScanner()
    }

    /**
     * Show the completed scan results
     */
    private fun completeScan(
        expiryMonth: String?,
        expiryYear: String?,
        cardNumber: String?,
        issuer: String?,
        name: String?,
        error: String?
    ) {
        AlertDialog.Builder(requireContext())
            .setMessage(
                String.format(
                    Locale.getDefault(),
                    "%s\n%s\n%s/%s\n%s\n%s",
                    cardNumber,
                    issuer,
                    expiryMonth,
                    expiryYear,
                    name,
                    error
                )
            )
            .show()
//        closeScanner()
    }

    /**
     * Close the scanner.
     */
//    private fun closeScanner() {
//        setFlashlightState(false)
//        scanCardButton.setVisibility(View.VISIBLE)
//        scanView.setVisibility(View.GONE)
//        setStateNotFound()
//        cameraAdapter.unbindFromLifecycle(this)
//        if (cardScanFlow != null) {
//            cardScanFlow.cancelFlow()
//        }
//        uploadScanStats(
//            this,
//            instanceId,
//            scanId,
//            fromContext.fromContext(this),
//            AppDetails.fromContext(this),
//            ScanStatistics.fromStats()
//        )
//    }

    override fun onCameraAccessError(cause: Throwable?) {
        TODO("Not yet implemented")
    }

    override fun onCameraOpenError(cause: Throwable?) {
        TODO("Not yet implemented")
    }

    override fun onCameraUnsupportedError(cause: Throwable?) {
        TODO("Not yet implemented")
    }

    override fun onAnalyzerFailure(t: Throwable): Boolean {
        TODO("Not yet implemented")
    }

    override fun onResultFailure(t: Throwable): Boolean {
        TODO("Not yet implemented")
    }
}
