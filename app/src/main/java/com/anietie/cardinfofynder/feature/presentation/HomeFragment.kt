package com.anietie.cardinfofynder.feature.presentation

import android.R.attr.data
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import cards.pay.paycardsrecognizer.sdk.ScanCardIntent
import com.anietie.cardinfofynder.R
import com.anietie.cardinfofynder.databinding.FragmentHomeBinding
import com.getbouncer.cardscan.ui.CardScanActivity
import com.getbouncer.cardscan.ui.CardScanActivityResult
import com.getbouncer.cardscan.ui.CardScanActivityResultHandler
import com.igorwojda.showcase.base.presentation.extension.setOnDebouncedClickListener
import com.microblink.blinkcard.entities.recognizers.Recognizer
import com.microblink.blinkcard.entities.recognizers.RecognizerBundle
import com.microblink.blinkcard.entities.recognizers.blinkcard.BlinkCardRecognizer
import com.microblink.blinkcard.uisettings.ActivityRunner
import com.microblink.blinkcard.uisettings.BlinkCardUISettings
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : Fragment(), CardScanActivityResultHandler {

    private val API_KEY = "ugIud-H7KoE5tEEqhTZ0M-BYtrpVAgh5"
    private val REQUEST_CODE_SCAN_CARD = 1
    private val REQUEST_CODE_BLINK_CARD = 2
    private lateinit var blinkcardRecognizer: BlinkCardRecognizer
    private lateinit var recognizerBundle: RecognizerBundle

    private val cardInfoViewModel: CardInfoViewModel by sharedViewModel()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        blinkcardRecognizer = BlinkCardRecognizer()
        recognizerBundle = RecognizerBundle(blinkcardRecognizer)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            cardNumberEditText.doAfterTextChanged {
                activateSubmitButton()
            }

            submitCardNumberButton.setOnDebouncedClickListener {
                val cardNumber = binding.cardNumberEditText.text.toString()
                cardInfoViewModel.getCardInfo(cardNumber)
                findNavController().navigate(R.id.cardInfoFragment)
            }

            blinkCardButton.setOnDebouncedClickListener {
                scanBlinkCard()
            }

            scanCardButton.setOnDebouncedClickListener {

                CardScanActivity.start(
                    fragment = this@HomeFragment,
                    apiKey = API_KEY,
                    enableEnterCardManually = true,

                    // expiry extraction is in beta. See the comment below.
                    enableExpiryExtraction = true,

                    // name extraction is in beta. See the comment below.
                    enableNameExtraction = true
                )
            }
        }

        /*
         * To test name and/or expiry extraction, please first provision an API
         * key, then reach out to support@getbouncer.com with details about your
         * use case and estimated volumes.
         *
         * If you are not planning to use name or expiry extraction, you can
         * omit the line below.
         */
        CardScanActivity.warmUp(
            context = requireContext(),
            apiKey = API_KEY,
            initializeNameAndExpiryExtraction = true
        )
    }

    private fun activateSubmitButton() {
        binding.submitCardNumberButton.isEnabled = binding.cardNumberEditText.text?.length!! == 8
    }

    private fun scanBlinkCard() {
        val settings = BlinkCardUISettings(recognizerBundle)
        ActivityRunner.startActivityForResult(requireActivity(), REQUEST_CODE_BLINK_CARD, settings)
    }

    private fun scanPayCard() {
        val intent: Intent = ScanCardIntent.Builder(requireContext()).build()
        startActivityForResult(intent, REQUEST_CODE_SCAN_CARD)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (CardScanActivity.isScanResult(requestCode)) {
            CardScanActivity.parseScanResult(resultCode, data, this)
        }

        if (requestCode == REQUEST_CODE_BLINK_CARD) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                // load the data into all recognizers bundled within your RecognizerBundle
                recognizerBundle.loadFromIntent(data)

                // now every recognizer object that was bundled within RecognizerBundle
                // has been updated with results obtained during scanning session

                // you can get the result by invoking getResult on recognizer
                val result: BlinkCardRecognizer.Result = blinkcardRecognizer.result
                if (result.resultState == Recognizer.Result.State.Valid) {
                    // result is valid, you can use it however you wish
                    val pan8Digits = result.cardNumber.substring(0, 7)
                    Log.d("PanLength", "cardScanned: ${pan8Digits.length}")
                    Log.d("ScanId", "cardScanned: ${result.cardNumber}")
                    Log.d("Card Owner", "onActivityResult: ${result.owner}")
                    cardInfoViewModel.getCardInfo(pan8Digits)
                    findNavController().navigate(R.id.cardInfoFragment)
                }
            }
        }
    }

    override fun analyzerFailure(scanId: String?) {
        Toast.makeText(requireContext(), "Unable to scan card. Make sure you are scanning a debit/credit card", Toast.LENGTH_SHORT).show()
    }

    override fun cameraError(scanId: String?) {
        Toast.makeText(requireContext(), "Camera not supported", Toast.LENGTH_SHORT).show()
    }

    override fun canceledUnknown(scanId: String?) {
    }

    override fun cardScanned(scanId: String?, scanResult: CardScanActivityResult) {
        val pan8Digits = scanResult.pan?.substring(0, 7)
        Log.d("PanLength", "cardScanned: ${pan8Digits!!.length}")
        Log.d("ScanId", "cardScanned: ${scanResult.pan}}")
        cardInfoViewModel.getCardInfo(pan8Digits)
        findNavController().navigate(R.id.cardInfoFragment)
    }

    override fun enterManually(scanId: String?) {
        findNavController().navigate(R.id.homeFragment)
    }

    override fun userCanceled(scanId: String?) {
        findNavController().navigate(R.id.homeFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
