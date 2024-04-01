package com.example.taskfour.view

import android.os.Bundle
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

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).hideBottomNavigation()

        val currentUser = auth.currentUser
        if (currentUser != null) {
            navigateToProfile()
        }
        binding.registerButton.setOnClickListener {
            registerButton()
        }
    }
    fun registerButton(){
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "Kayıt işlemi başarılı", Toast.LENGTH_LONG).show()
                    navigateToProfile()
                } else {
                    val exception = task.exception
                    Toast.makeText(requireContext(), "Kayıt işlemi başarısız: ${exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }

    fun navigateToProfile() {
        findNavController().navigate(R.id.action_loginFragment_to_listFragment)
    }
}