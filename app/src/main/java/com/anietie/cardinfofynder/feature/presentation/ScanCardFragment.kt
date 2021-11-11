package com.anietie.cardinfofynder.feature.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.anietie.cardinfofynder.databinding.FragmentScanCardBinding

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
}