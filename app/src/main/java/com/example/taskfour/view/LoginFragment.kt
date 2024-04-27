package com.example.taskfour.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.taskfour.MainActivity
import com.example.taskfour.R
import com.example.taskfour.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        with(binding){
            buttonGiris.setOnClickListener {
                authenticateUser(true)
            }
            buttonKayit.setOnClickListener {
                authenticateUser(false)
            }
        }
    }

    private fun authenticateUser(isLogin: Boolean) {
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        if (isLogin) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "signInWithEmail:success")
                        navigateToListFragment()
                    } else {
                        val exception = task.exception
                        Log.d(TAG, "signInWithEmail:failure", exception)
                        Toast.makeText(
                            requireContext(),
                            "Giriş işlemi başarısız: ${exception?.message}",
                            Toast.LENGTH_LONG,
                        ).show()
                    }
                }
        } else {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "createUserWithEmail:success")
                    } else {
                        val exception = task.exception
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            requireContext(),
                            "Kayıt işlemi başarısız: ${exception?.message}",
                            Toast.LENGTH_LONG,
                        ).show()
                    }
                }
        }
    }

    private fun navigateToListFragment() {
        findNavController().navigate(R.id.action_loginFragment_to_listFragment)
    }

    companion object {
        private const val TAG = "LoginFragment"
    }
}