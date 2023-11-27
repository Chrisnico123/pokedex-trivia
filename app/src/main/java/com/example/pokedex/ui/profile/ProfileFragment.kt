package com.example.pokedex.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.pokedex.LoginActivity
import com.example.pokedex.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        // Ambil data username dan email dari ProfileViewModel
        val username = profileViewModel.getUsername()
        val email = profileViewModel.getEmail()

        // Set data username dan email ke tampilan
        binding.tvUsername.text = username
        binding.tvEmail.text = email

        // Setup listener untuk tombol logout
        binding.btnLogout.setOnClickListener {
            profileViewModel.logout()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            // Tambahkan kode untuk navigasi atau aksi setelah logout
        }
        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
