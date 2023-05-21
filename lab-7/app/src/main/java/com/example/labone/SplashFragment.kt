package com.example.labone

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SplashFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SplashFragment : Fragment() {
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
        val root = inflater.inflate(R.layout.fragment_splash, container, false)
        val navController = NavHostFragment.findNavController(this)

        val preferences = requireActivity().getSharedPreferences("auth", Context.MODE_PRIVATE)
        val isRegistered = preferences.getBoolean("isRegistered", false)
        val isAutoAuth = preferences.getBoolean("isAutoAuth", false)

        val registrationId = R.id.registration_fragment
        val loginId = R.id.login_fragment
        val phoneListId = R.id.first_fragment

        if (!isRegistered) {
            // Переходим на экран регистрации
            navController.navigate(registrationId)
        }

        if (isRegistered) {
            if (isAutoAuth) {
                // Переходим на экран со списком
                navController.navigate(phoneListId)
                return root
            }
            // Переходим на экран логина
            navController.navigate(loginId)
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
         * @return A new instance of fragment SplashFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SplashFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}