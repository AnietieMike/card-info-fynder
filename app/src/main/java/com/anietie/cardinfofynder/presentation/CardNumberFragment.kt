package com.anietie.cardinfofynder.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.anietie.cardinfofynder.databinding.FragmentEnterCardNumberBinding

class CardNumberFragment : Fragment() {

    private var _binding: FragmentEnterCardNumberBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentEnterCardNumberBinding.inflate(inflater, container, false)
        return binding.root
    }
}