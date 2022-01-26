package com.anietie.cardinfofynder.feature.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.anietie.cardinfofynder.core.state.ResponseState
import com.anietie.cardinfofynder.databinding.FragmentCardInfoBinding
import com.igorwojda.showcase.base.presentation.extension.hide
import com.igorwojda.showcase.base.presentation.extension.show
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CardInfoFragment : Fragment() {

    private val args: CardInfoFragmentArgs by navArgs()
    private val cardInfoViewModel: CardInfoViewModel by sharedViewModel()
    private var _binding: FragmentCardInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCardInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        subscribeObserver()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObserver()
    }

    private fun subscribeObserver() {
        cardInfoViewModel.result.observe(
            viewLifecycleOwner,
            { result ->
                Log.d("ResultsSuccess", "subscribeObservers: $result")
                when (result.status) {
                    ResponseState.Status.SUCCESS -> {
                        binding.progressBar.hide()
                        binding.imageView2.hide()
                        binding.errorTextView.hide()
                        result.data?.let { cardInfo ->
                            with(binding) {
                                Log.d("ResultsData", "subscribeObservers: $cardInfo")
                                progressBar.hide()
                                cardBrandTextView.text = cardInfo.scheme.uppercase()
                                cardTypeTextView.text = cardInfo.type.uppercase()
                                bankTextView.text = cardInfo.bank.name?.uppercase() ?: "Information not provided"
                                cardNoTextView.text = args.cardNumber
                                expiryDateTextView.text = args.cardExpiryDate
                                cvvTextView.text = args.cvv
                                cardTypeTitleTextView.show()
                                cardBrandTitleTextView.show()
                                bankTitleTextView.show()
                                cardNumberTextView.show()
                                cardNoTextView.show()
                                cardBrandTextView.show()
                                cardTypeTextView.show()
                                bankTextView.show()
                                expiryTitleTextView.show()
                                expiryDateTextView.show()
                                cvvTitleTextView.show()
                                cvvTextView.show()
                            }
                        }
                    }
                    ResponseState.Status.ERROR -> {
                        result.message?.let {
                            with(binding) {
                                imageView2.show()
                                errorTextView.text = it
                                errorTextView.show()
                                progressBar.hide()
                                cardBrandTitleTextView.hide()
                                cardBrandTextView.hide()
                                cardTypeTitleTextView.hide()
                                cardTypeTextView.hide()
                                bankTitleTextView.hide()
                                bankTextView.hide()
                                cardNumberTextView.hide()
                                cardNoTextView.hide()
                                expiryTitleTextView.hide()
                                expiryDateTextView.hide()
                                cvvTitleTextView.hide()
                                cvvTextView.hide()
                            }
                        }
                    }
                    ResponseState.Status.LOADING -> {
                        with(binding) {
                            progressBar.show()
                            cardBrandTitleTextView.hide()
                            cardBrandTextView.hide()
                            cardTypeTitleTextView.hide()
                            cardTypeTextView.hide()
                            bankTitleTextView.hide()
                            bankTextView.hide()
                            cardNumberTextView.hide()
                            cardNoTextView.hide()
                            expiryTitleTextView.hide()
                            expiryDateTextView.hide()
                            cvvTitleTextView.hide()
                            cvvTextView.hide()
                            imageView2.hide()
                            errorTextView.hide()
                        }
                    }
                }
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
