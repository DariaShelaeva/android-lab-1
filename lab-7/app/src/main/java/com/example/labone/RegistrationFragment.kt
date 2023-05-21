package com.example.labone

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.FirebaseAuth

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegistrationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegistrationFragment : Fragment() {
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
        val root = inflater.inflate(R.layout.fragment_registration, container, false)
        val context = requireContext()
        val navController = NavHostFragment.findNavController(this)

        val phoneListId = R.id.first_fragment

        val preferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE)

        val authTypePhoneButton = root.findViewById<TextView>(R.id.auth_type_phone)
        val authTypeEmailButton = root.findViewById<TextView>(R.id.auth_type_email)

        val loginField = root.findViewById<EditText>(R.id.field_login)
        val passwordField = root.findViewById<EditText>(R.id.field_password)
        val passwordConfirmField = root.findViewById<EditText>(R.id.field_password_confirm)
        val confirmButton = root.findViewById<Button>(R.id.confirm_button)

        val firebaseAuth = FirebaseAuth.getInstance()

        fun registerUser() {
            firebaseAuth.createUserWithEmailAndPassword(
                loginField.text.toString(),
                passwordField.text.toString(),
            ).addOnSuccessListener {
                // Если всё хорошо, переходим на экран
                preferences.edit().putBoolean("isRegistered", true).apply()
                navController.navigate(phoneListId)
            }.addOnFailureListener { exception ->
                // Показываем ошибку
                Toast.makeText(context, exception.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }

        authTypePhoneButton.setOnClickListener{
            if (!authTypePhoneButton.typeface.isBold) {
                // Меняем вид кнопки логина по телефону
                authTypePhoneButton.typeface = Typeface.create(authTypePhoneButton.typeface, Typeface.BOLD)
                authTypePhoneButton.setTextColor(ContextCompat.getColor(context, R.color.primary))
                // Меняем вид кнопки логина по почте
                authTypeEmailButton.typeface = Typeface.create(authTypeEmailButton.typeface, Typeface.NORMAL)
                authTypeEmailButton.setTextColor(ContextCompat.getColor(context, R.color.foreground_primary))
                // Меням поле логина
                loginField.hint = "+7"
                loginField.setText("")
                loginField.inputType = InputType.TYPE_CLASS_PHONE
            }
        }

        authTypeEmailButton.setOnClickListener{
            if (!authTypeEmailButton.typeface.isBold) {
                // Меняем вид кнопки логина по телефону
                authTypeEmailButton.typeface = Typeface.create(authTypeEmailButton.typeface, Typeface.BOLD)
                authTypeEmailButton.setTextColor(ContextCompat.getColor(context, R.color.primary))
                // Меняем вид кнопки логина по почте
                authTypePhoneButton.typeface = Typeface.create(authTypePhoneButton.typeface, Typeface.NORMAL)
                authTypePhoneButton.setTextColor(ContextCompat.getColor(context, R.color.foreground_primary))
                // Меням поле логина
                loginField.hint = "Введите email"
                loginField.setText("")
                loginField.inputType =
                    (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
            }
        }

        confirmButton.setOnClickListener {
            val emailRegex = "\\w+@\\w+[.]\\w+".toRegex()
            val phoneRegex = "^[+]7\\d{10}\$".toRegex()

            if (loginField.inputType == InputType.TYPE_CLASS_PHONE && !phoneRegex.matches(loginField.text)) {
                Toast.makeText(context, "Неправильный формат телефона", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (loginField.inputType == (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS) && !emailRegex.matches(loginField.text)) {
                Toast.makeText(context, "Неправильный формат почты", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (passwordField.text.length < 8) {
                Toast.makeText(context, "Пароль должен содержать минимум 8 символов", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (passwordField.text.toString() != passwordConfirmField.text.toString()) {
                Toast.makeText(context, "Пароли должны совпадать", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Регестрируем пользователя
            registerUser()
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
         * @return A new instance of fragment RegistrationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegistrationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}