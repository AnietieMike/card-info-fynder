package com.anietie.cardinfofynder.feature.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.anietie.cardinfofynder.databinding.FragmentCardInfoBinding

class CardInfoFragment : Fragment() {

    private val args: CardInfoFragmentArgs by navArgs()
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cardBrandTextView.text = args.cardBrand
        binding.cardTypeTextView.text = args.cardType
        binding.bankTextView.text = args.bank
        binding.countryTextView.text = args.country
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}