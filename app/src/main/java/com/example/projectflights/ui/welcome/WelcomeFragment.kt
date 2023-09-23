package com.example.projectflights.ui.welcome

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.projectflights.R
import com.example.projectflights.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {

    private var _binding: FragmentWelcomeBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: WelcomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this)[WelcomeViewModel::class.java]

        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)

        binding.btnWelcome.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_navigation_home)
        }

        return binding.root;
    }


}