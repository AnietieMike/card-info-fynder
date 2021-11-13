package com.anietie.cardinfofynder.feature.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.anietie.cardinfofynder.R
import com.anietie.cardinfofynder.databinding.FragmentHomeBinding
import com.igorwojda.showcase.base.presentation.extension.setOnDebouncedClickListener
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : Fragment() {

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cardNumberEditText.doAfterTextChanged {
            activateSubmitButton()
        }

        binding.submitCardNumberButton.setOnDebouncedClickListener {
            val cardNumber = binding.cardNumberEditText.text.toString()
            cardInfoViewModel.getCardInfo(cardNumber)
            findNavController().navigate(R.id.cardInfoFragment)
        }
    }

    private fun activateSubmitButton() {
        binding.submitCardNumberButton.isEnabled = binding.cardNumberEditText.text?.length!! == 8
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
