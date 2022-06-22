package com.example.loginvalidation.view.fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.loginvalidation.databinding.FragmentLanguageBinding
import com.example.loginvalidation.view.HomeActivity
import java.util.*


class LanguageFragment : Fragment() {
    lateinit var binding: FragmentLanguageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLanguageBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.hindi.setOnClickListener {
            setLocale("hi")
            val intent = Intent(activity,HomeActivity::class.java)
            startActivity(intent)
            Toast.makeText(context,"App Language Changed In Hindi", Toast.LENGTH_SHORT).show()
        }
        binding.english.setOnClickListener {
            setLocale("en")
            val intent = Intent(activity,HomeActivity::class.java)
            startActivity(intent)
            Toast.makeText(context,"App Language Changed In Hindi", Toast.LENGTH_SHORT).show()
        }
        binding.urdu.setOnClickListener {
            setLocale("ur")
            val intent = Intent(activity,HomeActivity::class.java)
            startActivity(intent)
            Toast.makeText(context,"App Language Changed In Hindi", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setLocale(language : String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val configuration = resources.configuration
        configuration.setLocale(locale)

        resources.updateConfiguration(configuration,resources.displayMetrics)
    }
}