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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Firebase auth instance
        auth = FirebaseAuth.getInstance()

        binding.buttonGiris.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            // E-posta ve şifre ile giriş yap
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Giriş başarılı ise yapılacak işlemler
                        Log.d(TAG, "signInWithEmail:success")
                        // Örneğin, kullanıcıyı ana ekrana yönlendirme
                        findNavController().navigate(R.id.action_loginFragment_to_listFragment)
                    } else {
                        // Giriş başarısız ise kullanıcıya uyarı göster
                        Log.d(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            requireContext(), "Giriş başarısız, lütfen tekrar deneyin.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

        binding.buttonKayit.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            // E-posta ve şifre ile kullanıcı kaydı yap
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Kayıt başarılı ise yapılacak işlemler
                        Log.d(TAG, "createUserWithEmail:success")
                        // Örneğin, kullanıcıyı ana ekrana yönlendirme
                    } else {
                        // Kayıt başarısız ise kullanıcıya uyarı göster
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            requireContext(), "Kayıt başarısız, lütfen tekrar deneyin.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

    companion object {
        private const val TAG = "LoginFragment"
    }
}