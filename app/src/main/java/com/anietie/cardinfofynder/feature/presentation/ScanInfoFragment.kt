package com.anietie.cardinfofynder.feature.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.anietie.cardinfofynder.databinding.FragmentScanInfoBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ScanInfoFragment : Fragment() {

    private val args: ScanInfoFragmentArgs by navArgs()
    private val cardInfoViewModel: CardInfoViewModel by sharedViewModel()
    private var _binding: FragmentScanInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentScanInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setupViews()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        with(binding) {
            cardNoTextView.text = args.cardNumber
            expiryDateTextView.text = args.cardExpiryDate
            cvvTextView.text = args.cvv
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
