package com.example.labone

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.FirebaseAuth

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_login, container, false)
        val context = requireContext()
        val navController = NavHostFragment.findNavController(this)

        val phoneListId = R.id.first_fragment
        val registrationId = R.id.registration_fragment

        val loginField = root.findViewById<EditText>(R.id.field_login)
        val passwordField = root.findViewById<EditText>(R.id.field_password)
        val confirmButton = root.findViewById<Button>(R.id.confirm_button)
        val checkboxAutoAuth = root.findViewById<CheckBox>(R.id.check_auto_login)

        val preferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
        val login = preferences.getString("login", null)
        val password = preferences.getString("password", null)
        val phoneRegex = "^[+]7\\d{10}\$".toRegex()

        val firebaseAuth = FirebaseAuth.getInstance()

        fun loginUser() {
            firebaseAuth.signInWithEmailAndPassword(
                loginField.text.toString(),
                passwordField.text.toString(),
            ).addOnSuccessListener {
                preferences.edit().putBoolean("isAutoAuth", checkboxAutoAuth.isChecked).apply()
                navController.navigate(phoneListId)
            }.addOnFailureListener { exception ->
                Toast.makeText(context, exception.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }

        // Если почему-то нет данных, то переходим обратно на регистрацию
        if (login == null || password == null) {
            Toast.makeText(context, "Ошибка авторизации", Toast.LENGTH_SHORT).show()
            navController.navigate(registrationId)
            return root
        }

        // Если при регистрации был телефон, то меняет тип инпута
        if (phoneRegex.matches(login)) {
            loginField.hint = "+7"
            loginField.inputType = InputType.TYPE_CLASS_PHONE
        }

        confirmButton.setOnClickListener {
            loginUser()
        }

        return root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}