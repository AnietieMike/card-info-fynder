package com.anietie.cardinfofynder.feature.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.anietie.cardinfofynder.R
import com.anietie.cardinfofynder.databinding.FragmentScanCardBinding
import com.igorwojda.showcase.base.presentation.extension.setOnDebouncedClickListener

class ScanCardFragment : Fragment() {

    private var _binding: FragmentScanCardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentScanCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            scanCardButton.text = getString(R.string.scan_card)
            scanCardButton.setOnDebouncedClickListener {
                // cameraView is a custom View which provides camera preview
                cameraView.captureImage { _, bytes ->
                    // Get the Bitmap from the captured shot and use it to make the API call
//                    getCardDetails(bytes)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        binding.cameraView.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.cameraView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.cameraView.onPause()
    }

    override fun onStop() {
        super.onStop()
        binding.cameraView.onStop()
    }

//    private fun getCardDetails(bytes: ByteArray) {
//        val image = FirebaseVisionImage.fromByteArray(bytes, FirebaseVisionImageMetadata())
//        val firebaseVisionTextDetector = FirebaseVision.getInstance().cloudTextRecognizer
//
//        firebaseVisionTextDetector.processImage(image)
//            .addOnSuccessListener {
//                val words = it.text.split("\n")
//                for (word in words) {
//                    Log.e("TAG", word)
//                    // REGEX for detecting a credit card
//                    if (word.replace(" ", "").matches(Regex("^(?:4[0-9]{12}(?:[0-9]{3})?|[25][1-7][0-9]{14}|6(?:011|5[0-9][0-9])[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35\\d{3})\\d{11})\$")))
//                        binding.cardNumberTextView.text = word
//                }
//            }
//            .addOnFailureListener {
//                Toast.makeText(requireContext(), "Sorry, something went wrong!", Toast.LENGTH_SHORT).show()
//            }
//    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        binding.cameraView.onRequestPermissionsResult(100, permissions, grantResults)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
