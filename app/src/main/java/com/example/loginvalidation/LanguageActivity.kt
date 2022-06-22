package com.example.loginvalidation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.textclassifier.TextLanguage
import androidx.appcompat.app.AppCompatActivity
import com.example.loginvalidation.databinding.ActivityLanguageBinding
import com.example.loginvalidation.view.HomeActivity
import org.intellij.lang.annotations.Language
import java.util.*

class LanguageActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var mBinding: ActivityLanguageBinding
    var context : Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityLanguageBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mBinding.hindi.setOnClickListener(this)
        mBinding.english.setOnClickListener(this)
        mBinding.urdu.setOnClickListener(this)
    }
    override  fun onClick(item : View?){
        when(item){
            mBinding.english ->
            {
                setAppLocale(this,"en")
                val intent = Intent(this,HomeActivity::class.java)
                startActivity(intent)
            }
            mBinding.hindi ->
            {
                setAppLocale(this,"hi")
                val intent = Intent(this,HomeActivity::class.java)
                startActivity(intent)
            }
            mBinding.urdu ->
            {
                setAppLocale(this,"ur")
                val intent = Intent(this,HomeActivity::class.java)
                startActivity(intent)
            }
        }

    }
    private fun setAppLocale(context: LanguageActivity,language: String){
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        context.createConfigurationContext(config)
        context.resources.updateConfiguration(config,context.resources.displayMetrics)
    }
}