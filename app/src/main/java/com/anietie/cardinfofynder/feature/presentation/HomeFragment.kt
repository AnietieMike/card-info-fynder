package com.anietie.cardinfofynder.feature.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.anietie.cardinfofynder.core.state.ResponseState
import com.anietie.cardinfofynder.databinding.FragmentHomeBinding
import com.igorwojda.showcase.base.presentation.extension.setOnDebouncedClickListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val cardInfoViewModel: CardInfoViewModel by viewModel()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeObserver()

        binding.cardNumberEditText.doAfterTextChanged {
            activateSubmitButton()
        }

        binding.submitCardNumberButton.setOnDebouncedClickListener {
            val cardNumber = binding.cardNumberEditText.text.toString()
            cardInfoViewModel.getCardInfo(cardNumber)
        }
    }

    private fun subscribeObserver() {
        cardInfoViewModel.result.observe(
            viewLifecycleOwner,
            Observer { result ->
                Log.d("ResultsSuccess", "subscribeObservers: $result")
                when (result.status) {
                    ResponseState.Status.SUCCESS -> {
//                        binding.progressBar?.visibility = View.GONE
                        result.data?.let { cardInfo ->
                            Log.d("ResultsData", "subscribeObservers: $cardInfo")
                            cardInfoViewModel.navigateToCardDetails(
                                cardInfo.brand,
                                cardInfo.type,
                                cardInfo.bank.name,
                                cardInfo.country.name
                            )

                            Log.d("Navigaate", "subscribeObserver: $cardInfo")
                        }
                    }
                    ResponseState.Status.ERROR -> {
//                        binding.progressBar?.visibility = View.INVISIBLE
                        result.message?.let {
                            Toast.makeText(
                                requireContext(),
                                "Unable to load card information",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    ResponseState.Status.LOADING -> {
                        // Implement a progress bar
                    }

                }
            }
        )
    }

    private fun activateSubmitButton() {
        binding.submitCardNumberButton.isEnabled = binding.cardNumberEditText.text?.length!! >= 8
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
